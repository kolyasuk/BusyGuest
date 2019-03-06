package iful.edu.bg.service;

import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService/* implements UserDetailsService */ {

	// @Autowired
	// AccountServiceImpl accountServiceImpl;
	//
	// @Override
	// public UserDetails loadUserByUsername(String username) throws
	// UsernameNotFoundException {
	// try {
	// Account account = accountServiceImpl.findByEmail(username);
	// if (account != null) {
	// List<GrantedAuthority> authorities = account.getRoles().stream().map(role ->
	// new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
	// return new
	// org.springframework.security.core.userdetails.User(account.getEmail(),
	// account.getPassword(), authorities);
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return null;
	//
	// }

}
