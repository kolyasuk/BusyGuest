package iful.edu.bg.config;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomizeAuthenticationSuccessHandler /* implements AuthenticationSuccessHandler */ {

	// @Override
	// public void onAuthenticationSuccess(HttpServletRequest request,
	// HttpServletResponse response, Authentication authentication) throws
	// IOException, ServletException {
	// // set our response to OK status
	// response.setStatus(HttpServletResponse.SC_OK);
	//
	// for (GrantedAuthority auth : authentication.getAuthorities()) {
	// if ("ADMIN".equals(auth.getAuthority())) {
	// response.sendRedirect("/dashboard");
	// }
	// if ("CLIENT".equals(auth.getAuthority())) {
	// response.sendRedirect("/client");
	// }
	//
	// if ("ESTBL".equals(auth.getAuthority())) {
	// response.sendRedirect("/estb/" + authentication.getName());
	// }
	// }
	// }

}
