package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RubrosDTO implements Serializable {
	private Long id;
	private String nombre;
	private Double valor;
	private Boolean isActive;
}
