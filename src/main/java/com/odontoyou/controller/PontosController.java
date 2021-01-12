package com.odontoyou.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odontoyou.model.Pontos;
import com.odontoyou.repo.PontosRepo;
import com.odontoyou.service.CPFService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/pontos", produces = "application/json")
public class PontosController {

	@Autowired
	PontosRepo repo;
	@Autowired
	CPFService cpfService;
	
	@GetMapping
	@ApiOperation("Lista todos os pontos do sistema")
	public List<Pontos> listar() {
		return repo.findAll();
	}
	
	@GetMapping("/dia/{cpf}/{dia}")
	@ApiOperation("Retorna o dia de um funcionário")
	public Optional<Pontos> getDiaFunc(@PathVariable(value = "cpf") String cpf, @PathVariable(value = "dia") String dia) {
		String _cpf = cpfService.ajustaCpf(cpf);
		return repo.findDiaFuncionario(_cpf, dia);
	}
	
	@GetMapping("/mes/{cpf}/{mesAno}")
	@ApiOperation("Retorna o mês de um funcionário")
	public List<Pontos> getMesFunc(@PathVariable(value = "cpf") String cpf, @PathVariable(value = "mesAno") String mesAno) {
		String _cpf = cpfService.ajustaCpf(cpf);
		return repo.findMesAnoFuncionario(_cpf, mesAno);
	}
	
	@PostMapping
	@ApiOperation("Salva o ponto. Seja entrada, saida ou ida e volta do almoço.")
	public Pontos salvar(@RequestBody Pontos pontos) {
		pontos.setCpf(cpfService.ajustaCpf(pontos.getCpf()));
		return repo.save(pontos);
	}
	
}
