package iful.edu.bg.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import iful.edu.bg.entity.User;
import iful.edu.bg.repository.TableRepository;
import iful.edu.bg.repository.UserRepository;
import iful.edu.bg.service.EstablishmentServiceImpl;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private EstablishmentServiceImpl establishmentServiceImpl;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
	@Value("${spring.profiles.active}")
	private String profile;
	
	@GetMapping
	public String main(Model model, @AuthenticationPrincipal User user) throws Exception {
		HashMap<Object, Object> data = new HashMap<>();

		if (user != null) {
			Optional<User> usse = userRepository.findById(user.getId());
			user.setPhone(usse.get().getPhone());
			

			data.put("profile", user);
			data.put("role", user.getRole().getName());
			data.put("establishments", establishmentServiceImpl.getEstablishmentList());
		}

		model.addAttribute("frontendData", data);
		model.addAttribute("isDevMode", "dev".equals(profile));
		return "index";
	}
	
	@GetMapping("/gs-guide-websocket/info")
	public String redirect(Model model, @AuthenticationPrincipal User user) throws Exception {
		HashMap<Object, Object> data = new HashMap<>();

		if (user != null) {
			Optional<User> usse = userRepository.findById(user.getId());
			user.setPhone(usse.get().getPhone());

			data.put("profile", user);
			data.put("role", user.getRole().getName());
			data.put("establishments", establishmentServiceImpl.getEstablishmentList());
		}

		model.addAttribute("frontendData", data);
		model.addAttribute("isDevMode", "dev".equals(profile));
		return "index";
	}

}
