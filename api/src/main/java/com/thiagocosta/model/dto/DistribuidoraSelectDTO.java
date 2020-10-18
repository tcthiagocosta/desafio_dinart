package com.thiagocosta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistribuidoraSelectDTO {

	private Integer id;
	
	private String nome;
}
