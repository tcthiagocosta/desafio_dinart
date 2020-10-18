package com.thiagocosta.model.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RetornoAgrupadoPorDistribuidoraDTO {
	
	@Data
	@NoArgsConstructor
	public static class InformacaoDistribuidoraDTO {
		private String cnpjDistribuidora;
		private String regiao;
		private String estado;
		private String municipio;
		private double valorCompra;
		private double valorVenda;
		private String combustivel;
		private String unidadeMedida;	
		public String dataColeta;
	}
	
	private String nomeDistribuidora;
	
	private List<InformacaoDistribuidoraDTO> listaInformacaoDistribuidoraDTO;
	
	
}
