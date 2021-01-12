package com.odontoyou.service;

import org.springframework.stereotype.Service;

@Service
public class CPFService {

	public String ajustaCpf(String cpf) {
		return cpf.replaceAll("[.-]", "");
	}
}
