package com.thiagocosta.model.dto;

import com.thiagocosta.model.entity.Combustivel;
import com.thiagocosta.model.entity.Distribuidora;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevendaDTO {
	
	private Integer id;
    private String dataColeta;
    private String valorCompra;
    private String valorVenda;
    private Distribuidora distribuidora;
    private Combustivel combustivel;

}
