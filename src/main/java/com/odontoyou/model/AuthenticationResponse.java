package com.odontoyou.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class AuthenticationResponse {

	private final String jwt;
	
	private String mensagem;
}
