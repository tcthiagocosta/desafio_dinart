package com.thiagocosta.model.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RetornoAgrupadoPorSiglaDTO {
	
	@Data
	@NoArgsConstructor
	public static class InformacaoAgrupadoPorSiglaDTO {
		private String nomeDistribuidora;
		private String cnpjDistribuidora;
		private String estado;
		private String municipio;
		private double valorCompra;
		private double valorVenda;
		private String combustivel;
		private String unidadeMedida;		
		public String dataColeta;
	}
	
	private String sigla;
	
	private List<InformacaoAgrupadoPorSiglaDTO> listaInformacaoAgrupadoPorSiglaDTO;
	
	
}
