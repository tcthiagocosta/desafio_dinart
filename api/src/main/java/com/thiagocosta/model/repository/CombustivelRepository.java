package com.thiagocosta.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagocosta.model.entity.Combustivel;


public interface CombustivelRepository extends JpaRepository<Combustivel, Integer> {
	
	 List<Combustivel> findAllByOrderByIdAsc();

   
}
