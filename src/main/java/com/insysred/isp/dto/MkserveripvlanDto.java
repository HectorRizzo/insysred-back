package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MkserveripvlanDto implements Serializable {

	private Long id;
	private Long idvlan;
	private String ip;
	private String estado;
	private Long idRouter;
	private Long idContrato;
	private Boolean esActivo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdvlan() {
		return idvlan;
	}
	public void setIdvlan(Long idvlan) {
		this.idvlan = idvlan;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Long getIdRouter() {
		return idRouter;
	}
	public void setIdRouter(Long idRouter) {
		this.idRouter = idRouter;
	}
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	public Boolean getEsActivo() {
		return esActivo;
	}
	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}

	
}
