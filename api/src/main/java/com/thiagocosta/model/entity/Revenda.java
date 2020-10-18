package com.thiagocosta.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Revenda {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne
	private Distribuidora distribuidora;

	@ManyToOne
	private Combustivel combustivel;

	@Column(name = "valor_venda", precision = 10, scale = 4)
	private double valorVenda;

	@Column(name = "valor_compra", precision = 10, scale = 4)
	private double valorCompra;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataColeta;

}
