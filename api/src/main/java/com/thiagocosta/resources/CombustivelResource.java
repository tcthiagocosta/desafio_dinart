package com.thiagocosta.resources;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagocosta.model.dto.CombustivelSelectDTO;
import com.thiagocosta.service.CombustivelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/combustivel")
@RequiredArgsConstructor
@Api
public class CombustivelResource {
	
	private final CombustivelService combustivelService; 
	
	@GetMapping
	@ApiOperation(value = "Retorne lista dos combustíveis")
	public List<CombustivelSelectDTO> obterTodos() {
        return combustivelService.obterTodos();
    }

}
