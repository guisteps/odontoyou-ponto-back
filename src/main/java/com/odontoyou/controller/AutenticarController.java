package com.odontoyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odontoyou.model.AuthenticationResponse;
import com.odontoyou.model.Users;
import com.odontoyou.service.CPFService;
import com.odontoyou.service.JwtUtil;
import com.odontoyou.service.MyUserDetailsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/autenticar", produces = "application/json")
public class AutenticarController {

	@Autowired
	AuthenticationManager authManager;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	MyUserDetailsService userDetailsService;
	@Autowired
	CPFService cpfService;
	
	@PostMapping
	@ApiOperation("Método de autenticação. Retorna um token JWT válido por 12hrs")
	public ResponseEntity<?> autenticar(@RequestBody Users user) {
		if (user == null || user.getCpf() == null || user.getSenha() == null)
			return ResponseEntity.badRequest().body(new AuthenticationResponse(null, "Dados Inválidos"));
		
		user.setCpf(cpfService.ajustaCpf(user.getCpf()));

		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getCpf(), user.getSenha()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(401).body(new AuthenticationResponse(null, "Não foi possível gerar o Token. Dados do usuário inválido"));
		}

		final UserDetails userDetail = userDetailsService.loadUserByUsername(user.getCpf());
		String jwt = jwtUtil.generateToken(userDetail);
		return ResponseEntity.ok(new AuthenticationResponse(jwt, null));
	}
	
}
