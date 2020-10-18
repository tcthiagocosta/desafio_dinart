package com.thiagocosta.resources;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagocosta.model.dto.DistribuidoraSelectDTO;
import com.thiagocosta.service.DistribuidoraService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/distribuidora")
@RequiredArgsConstructor
@Api
public class DistribuidoraResource {
	
	private final DistribuidoraService distribuidoraService;  
	
	@GetMapping
	@ApiOperation(value = "Retorne lista das Distribuidoras")
	public List<DistribuidoraSelectDTO> obterTodos() {
        return distribuidoraService.obterTodos();
    }

}
