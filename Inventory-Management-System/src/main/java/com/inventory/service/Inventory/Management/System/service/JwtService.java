package com.inventory.service.Inventory.Management.System.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
	
	public static final String SECRET = "your-secret-key";
	
	public String generateToken(String userName)
	{
		Map<String,Object> claims = new HashMap<>();
		
		return createToken(claims,userName);
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis())).
				setExpiration(new Date(System.currentTimeMillis()+1000*60*30)).signWith(SignatureAlgorithm.HS512,SECRET).compact();
	}

	
	
	 

	

}