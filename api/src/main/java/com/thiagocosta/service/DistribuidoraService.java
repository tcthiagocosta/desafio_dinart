package com.thiagocosta.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagocosta.model.dto.DistribuidoraSelectDTO;
import com.thiagocosta.model.entity.Combustivel;
import com.thiagocosta.model.entity.Distribuidora;
import com.thiagocosta.model.repository.DistribuidoraRepository;

@Service
public class DistribuidoraService {
	
	@Autowired
	private DistribuidoraRepository distribuidoraRepository;
	
	public List<DistribuidoraSelectDTO> obterTodos() {
		List<Distribuidora> list = distribuidoraRepository.findAllByOrderByIdAsc();
		
		List<DistribuidoraSelectDTO> listaRetorno =  new ArrayList<DistribuidoraSelectDTO>();
		
		for (Distribuidora distribuidora : list) {
			listaRetorno.add(DistribuidoraSelectDTO
					.builder()	
					.id(distribuidora.getId())
					.nome(distribuidora.getNome())
					.build());
			
		}
		
		return listaRetorno;
	}

	public Distribuidora getPorId(Integer id) throws Exception {
        return distribuidoraRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Distribuidora nao encontrada."));
                
    }
}
