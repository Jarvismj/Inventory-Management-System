package com.inventory.service.Inventory.Management.System.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.service.Inventory.Management.System.entity.UserInfo;
import com.inventory.service.Inventory.Management.System.service.UserService;

//Login Controller for Registration and Authentication
@RestController
public class UserLoginController {
	private final String register = "/register";
	private final String login = "/login";

	@Autowired
	private UserService service;

	//Register New User Details
	@PostMapping(register)
	public UserInfo register(@RequestBody UserInfo user) {
		return service.register(user);

	}
   //Login for New User
	@PostMapping(login)
	public String login(@RequestBody UserInfo user) {

		return service.verify(user);
	}

}
