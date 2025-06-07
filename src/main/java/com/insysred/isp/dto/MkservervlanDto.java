package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MkservervlanDto implements Serializable {
	private Long id;
	private String nombre;
	private String interfaz;
	private String comentario;
	private String ip;
	private String mascara;
	private Long idRouter;
	private Long idPadre;
	private Boolean esActivo;
	private String macAddress;
	private String mktVlanId;
	private String mktVlanNetwork;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(String interfaz) {
		this.interfaz = interfaz;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

	public Long getIdRouter() {
		return idRouter;
	}

	public void setIdRouter(Long idRouter) {
		this.idRouter = idRouter;
	}

	public Long getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
	}

	public Boolean getEsActivo() {
		return esActivo;
	}

	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMktVlanId() {
		return mktVlanId;
	}

	public void setMktVlanId(String mktVlanId) {
		this.mktVlanId = mktVlanId;
	}

	public String getMktVlanNetwork() {
		return mktVlanNetwork;
	}

	public void setMktVlanNetwork(String mktVlanNetwork) {
		this.mktVlanNetwork = mktVlanNetwork;
	}
 
}
