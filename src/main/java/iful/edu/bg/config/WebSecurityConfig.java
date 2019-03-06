package iful.edu.bg.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import iful.edu.bg.model.Role;
import iful.edu.bg.model.User;
import iful.edu.bg.repository.RoleRepository;
import iful.edu.bg.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/js/**", "/error**").permitAll().
			antMatchers("/visitor/**").hasAuthority("VISITOR").
			antMatchers("/admin/**").hasRole("ADMIN").
			antMatchers("/estb/**").hasRole("ESTB").
			anyRequest().authenticated().
			and().
			logout().logoutSuccessUrl("/").permitAll().
			and().csrf().disable();
	}

	@Bean
	public PrincipalExtractor principalExtractor(UserRepository userRepository) {
		return map -> {
			String id = (String) map.get("sub");

			User user = userRepository.findById(id).orElseGet(() -> {
				User newUser = new User();
				Role visitorRole = roleRepository.findByRole("VISITOR");
				
				newUser.setRoles(new HashSet<Role>(Arrays.asList(visitorRole)));
				newUser.setId(id);
				newUser.setName((String) map.get("name"));
				newUser.setEmail((String) map.get("email"));
				newUser.setGender((String) map.get("gender"));
				newUser.setLocale((String) map.get("locale"));
				newUser.setUserpic((String) map.get("picture"));

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

		    return AuthorityUtils.createAuthorityList(user.get().getRoles().stream().map(role -> role.getRole()).toArray(String[]::new));

		};
	}
	

}
