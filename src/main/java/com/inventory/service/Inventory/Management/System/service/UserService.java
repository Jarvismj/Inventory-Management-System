package com.inventory.service.Inventory.Management.System.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventory.service.Inventory.Management.System.entity.UserInfo;
import com.inventory.service.Inventory.Management.System.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authManager;
	@Autowired
    private UserRepo repo;

	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	//Register New User
    public UserInfo register(UserInfo user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }

  //Verifying the User and Generating the Token
	public String verify(UserInfo user) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUsername());
		} else {
			return "fail";
		}
	}
}
