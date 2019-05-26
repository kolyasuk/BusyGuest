package iful.edu.bg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iful.edu.bg.service.EstablishmentServiceImpl;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private EstablishmentServiceImpl establishmentServiceImpl;

}
