package com.insysred.isp.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "mkserveripvlan")
@Data
public class Mkserveripvlan implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_vlan")
	private Long idvlan;

	@Column(name = "ip", length = 50)
	private String ip;

	@Column(name = "estado", length = 50)
	private String estado;

	@Column(name = "id_router")
	private Long idRouter;

	@Column(name = "id_contrato")
	private Long idContrato;

	@Column(name = "es_activo")
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
