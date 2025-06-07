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
@Table(name = "mkservervlan")
@Data
public class Mkservervlan implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", length = 250)
	private String nombre;

	@Column(name = "interfaz", length = 50)
	private String interfaz;

	@Column(name = "comentario", length = 250)
	private String comentario;

	@Column(name = "ip", length = 50)
	private String ip;

	@Column(name = "mascara", length = 50)
	private String mascara;

	@Column(name = "id_router")
	private Long idRouter;

	@Column(name = "id_padre")
	private Long idPadre;

	@Column(name = "es_activo")
	private Boolean esActivo;
	
	@Column(name = "mac_address", length = 50)
	private String macAddress;
	
	@Column(name = "mkt_vlan_id", length = 50)
	private String mktVlanId;

	@Column(name = "mkt_vlan_network", length = 50)
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
