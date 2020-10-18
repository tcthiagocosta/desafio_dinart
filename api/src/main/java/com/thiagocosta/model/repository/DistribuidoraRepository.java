package com.thiagocosta.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagocosta.model.entity.Distribuidora;


public interface DistribuidoraRepository extends JpaRepository<Distribuidora, Integer> {

	 List<Distribuidora> findAllByOrderByIdAsc();
}
