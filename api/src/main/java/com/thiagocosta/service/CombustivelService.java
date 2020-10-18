package com.thiagocosta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagocosta.model.dto.CombustivelSelectDTO;
import com.thiagocosta.model.entity.Combustivel;
import com.thiagocosta.model.entity.Usuario;
import com.thiagocosta.model.repository.CombustivelRepository;

@Service
public class CombustivelService {
	
	@Autowired
	private CombustivelRepository combustivelRepository; 
	
	public List<CombustivelSelectDTO> obterTodos() {
		List<Combustivel> lista =  combustivelRepository.findAllByOrderByIdAsc();
		
		List<CombustivelSelectDTO> listaRetorno =  new ArrayList<CombustivelSelectDTO>();
		
		for (Combustivel combustivel : lista) {
			listaRetorno.add(CombustivelSelectDTO
					.builder()	
					.id(combustivel.getId())
					.nome(combustivel.getNome())
					.build());
			
		}
		
		return listaRetorno;
	}
	
	public Combustivel getPorId(Integer id) throws Exception {
        return combustivelRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Combustível nao encontrado."));
                
    }

}
