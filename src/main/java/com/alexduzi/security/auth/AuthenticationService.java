package com.alexduzi.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alexduzi.security.config.JwtService;
import com.alexduzi.security.user.Role;
import com.alexduzi.security.user.User;
import com.alexduzi.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	

	public AuthenticationResponse register(RegisterRequest request) {
		 User user = new User(null, request.getFirstname(), request.getLastname(), request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.USER);
		 
		repository.save(user);
		
		var jwtToken = jwtService.generateToken(user);
		
		return new AuthenticationResponse(jwtToken);
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = repository.findByEmail(request.getEmail()).orElseThrow();
		
		var jwtToken = jwtService.generateToken(user);
		
		return new AuthenticationResponse(jwtToken);
	}
	
}
