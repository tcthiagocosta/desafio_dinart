package com.thiagocosta.model.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RetornoAgrupadoPorDataDTO {
	
	@Data
	@NoArgsConstructor
	public static class InformacaoDTO {
		private String nomeDistribuidora;
		private String cnpjDistribuidora;
		private String regiao;
		private String estado;
		private String municipio;
		private double valorCompra;
		private double valorVenda;
		private String combustivel;
		private String unidadeMedida;		
	}
	
	private String data;
	
	private List<InformacaoDTO> listaImformacao;
	
	
}
