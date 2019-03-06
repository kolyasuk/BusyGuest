package iful.edu.bg.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import iful.edu.bg.model.User;
import iful.edu.bg.service.EstablishmentServiceImpl;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private EstablishmentServiceImpl establishmentServiceImpl;

	@GetMapping
	public String main(Model model, @AuthenticationPrincipal User user) {
		HashMap<Object, Object> data = new HashMap<>();

		data.put("profile", user);
		if (user != null)
			data.put("estbs", establishmentServiceImpl.getEstablishmentList());

		model.addAttribute("frontendData", data);
		return "index";
	}
}
