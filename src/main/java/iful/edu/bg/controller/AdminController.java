package iful.edu.bg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iful.edu.bg.enums.Roles;
import iful.edu.bg.model.Role;
import iful.edu.bg.model.User;
import iful.edu.bg.repository.RoleRepository;
import iful.edu.bg.repository.UserRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	@PutMapping("user/{id}")
	public User updateUser(@PathVariable("id") User userFromDB, @RequestBody User user) {

		return userRepository.save(user);
	}

	@PutMapping("user/{id}/bussines")
	public User setRole(@PathVariable("id") User userFromDB) {
		Role role = roleRepository.findByName(Roles.ESTB);
		userFromDB.setRole(role);
		return userRepository.save(userFromDB);

	}

}
