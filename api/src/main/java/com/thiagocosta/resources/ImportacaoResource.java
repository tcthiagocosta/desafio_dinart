package com.thiagocosta.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thiagocosta.service.ImportacaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/importacao")
@RequiredArgsConstructor
@Api
public class ImportacaoResource {
	
	private final ImportacaoService service;
	
	@PostMapping
	@ApiOperation(value = "Importa arquivo CSV para base de dados")
    public ResponseEntity<?> arquivo (@RequestParam("file") MultipartFile file) {
    	try {
			service.importaArquivoCsv(file);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }

}
