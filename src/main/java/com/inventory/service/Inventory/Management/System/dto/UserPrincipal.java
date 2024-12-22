package com.inventory.service.Inventory.Management.System.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.inventory.service.Inventory.Management.System.entity.UserInfo;
//Defining Authorities 
public class UserPrincipal implements UserDetails {

	private int id;
	private String username; // Changed from 'name' to 'username' for clarity
	private String password;
	private List<GrantedAuthority> authorities;

	public UserPrincipal(UserInfo userInfo) {
		this.id = userInfo.getId();
		this.username = userInfo.getUsername(); // Assuming 'name' is used as 'username'
		this.password = userInfo.getPassword();

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

//Below methods are not required now
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
