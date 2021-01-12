package com.odontoyou.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.odontoyou.model.Pontos;

public interface PontosRepo extends JpaRepository<Pontos, Long> {

	@Query(value = "Select p FROM Pontos p WHERE p.cpf = ?1 AND p.dia = ?2")
	public Optional<Pontos> findDiaFuncionario(String cpf, String dia);
	
	@Query(value = "Select p FROM Pontos p WHERE p.cpf = ?1 AND p.dia LIKE ?2%")
	public List<Pontos> findMesAnoFuncionario(String cpf, String mesAno);
}
