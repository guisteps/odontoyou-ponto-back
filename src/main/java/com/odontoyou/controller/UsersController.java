package com.odontoyou.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odontoyou.model.Users;
import com.odontoyou.repo.UsersRepo;
import com.odontoyou.service.CPFService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UsersController {

	@Autowired
	UsersRepo repo;
	@Autowired
	PasswordEncoder pwdEncoder;
	@Autowired
	CPFService cpfService;

	@GetMapping
	@ApiOperation("Retorna lista de todos os usuários")
	public List<Users> listar() {
		return repo.findAll();
	}

	@GetMapping("/{cpf}")
	@ApiOperation("Retorna um único usuário")
	public Optional<Users> getUser(@PathVariable(value = "cpf") String cpf) {
		String _cpf = cpfService.ajustaCpf(cpf);
		return repo.findById(_cpf);
	}

	@PostMapping
	@ApiOperation("Salva informações de um novo usuário ou atualiza um já existente")
	public Users salvar(@RequestBody Users user) {
		user.setCpf(cpfService.ajustaCpf(user.getCpf()));
		user.setSenha(pwdEncoder.encode(user.getSenha()));
		return repo.save(user);
	}

}
