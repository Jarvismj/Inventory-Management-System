package com.inventory.service.Inventory.Management.System.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inventory.service.Inventory.Management.System.dto.UserPrincipal;
import com.inventory.service.Inventory.Management.System.entity.UserInfo;
import com.inventory.service.Inventory.Management.System.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	// Getting User Details from DB
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userRepo.findByUsername(username);
		if (user == null) {
			throw new RuntimeException("User Not Found. Please register with the details and then login.");
		}

		return new UserPrincipal(user);
	}
}
