package iful.edu.bg.config;

import java.util.Collections;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import iful.edu.bg.entity.Reputation;
import iful.edu.bg.entity.Role;
import iful.edu.bg.entity.User;
import iful.edu.bg.enums.Reputations;
import iful.edu.bg.enums.Roles;
import iful.edu.bg.repository.ReputationRepository;
import iful.edu.bg.repository.RoleRepository;
import iful.edu.bg.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ReputationRepository reputationRepository;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/js/**", "/error**").permitAll().
			antMatchers("/estb/establishment/{estbId}/table", "/visitor/{tableId}/bookedTable", "/visitor/bookedTable/{id}","/visitor/profile").
			hasAnyAuthority("VISITOR", "ESTB").
			antMatchers("/admin/user/{id}/bussines").hasAnyAuthority("VISITOR","ADMIN").
			antMatchers("/estb/establishment/**", "/estb/establishment/{id}/coordinates").hasAnyAuthority("VISITOR","ADMIN", "ESTB").
			antMatchers("/visitor/**").hasAuthority("VISITOR").
			antMatchers("/admin/**").hasAuthority("ADMIN").
			antMatchers("/estb/**").hasAuthority("ESTB").
			anyRequest().authenticated().and()
            .logout().logoutSuccessUrl("/").permitAll().
			and().csrf().disable();
	}

	@Bean
	public PrincipalExtractor principalExtractor(UserRepository userRepository, HttpServletRequest req) {
		return map -> {
			String id = (String) map.get("sub");
			
			User user = userRepository.findById(id).orElseGet(() -> {
				Role visitorRole = null;
				for (Cookie c : req.getCookies()) {
					if (c.getName().equals("bussines")) {
						visitorRole = roleRepository.findByName(Roles.ESTB);
						break;
					}
					else visitorRole = roleRepository.findByName(Roles.VISITOR);
				}  
				
				User newUser = new User();
	
				if(visitorRole.getName().equals(Roles.VISITOR)) {
					Reputation reputation = new Reputation(null, Reputations.UNRELIABLE, 0, 0);
					newUser.setReputation(reputationRepository.save(reputation));
				}
				
				newUser.setRole(visitorRole);
				newUser.setId(id);
				newUser.setName((String) map.get("name"));
				newUser.setEmail((String) map.get("email"));
				newUser.setUserpic((String) map.get("picture"));
				newUser.setPhone("");
				
				return newUser;
			});
			return userRepository.save(user);
		};
	}
	
	@Bean
	public AuthoritiesExtractor AuthoritiesExtractor(UserRepository userRepository) {
		return map -> {
			
		    String id = (String) map.get("sub");
		    Optional<User> user = userRepository.findById(id);
		    if(!user.isPresent())
		    	return Collections.<GrantedAuthority> emptyList();
		    return AuthorityUtils.createAuthorityList(user.get().getRole().getName().toString());

		};
	}
	

}
