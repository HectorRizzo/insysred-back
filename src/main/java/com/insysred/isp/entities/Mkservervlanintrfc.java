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
@Table(name = "mkservervlanintrfc")
@Data
public class Mkservervlanintrfc implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "interface_address", length = 50)
	private String interfaceAddress;

	@Column(name = "interface_actual_interface", length = 50)
	private String interfaceActualInterface;

	@Column(name = "interface_invalid")
	private Boolean interfaceInvalid;

	@Column(name = "interface_id", length = 50)
	private String interfaceId;

	@Column(name = "interface_dynamic")
	private Boolean interfaceDynamic;

	@Column(name = "interface_disabled")
	private Boolean interfaceDisabled;
	
	@Column(name = "interface_comment", length = 50)
	private String interfaceComment;

	@Column(name = "interface", length = 50)
	private String interfaceInterface;

	@Column(name = "interface_network", length = 50)
	private String interfaceNetwork;

	@Column(name = "server_id")
	private Long serverId;

	@Column(name = "vlan_id")
	private Long vlanId;

	@Column(name = "es_activo")
	private Boolean esActivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInterfaceAddress() {
		return interfaceAddress;
	}

	public void setInterfaceAddress(String interfaceAddress) {
		this.interfaceAddress = interfaceAddress;
	}

	public String getInterfaceActualInterface() {
		return interfaceActualInterface;
	}

	public void setInterfaceActualInterface(String interfaceActualInterface) {
		this.interfaceActualInterface = interfaceActualInterface;
	}

	public Boolean getInterfaceInvalid() {
		return interfaceInvalid;
	}

	public void setInterfaceInvalid(Boolean interfaceInvalid) {
		this.interfaceInvalid = interfaceInvalid;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public Boolean getInterfaceDynamic() {
		return interfaceDynamic;
	}

	public void setInterfaceDynamic(Boolean interfaceDynamic) {
		this.interfaceDynamic = interfaceDynamic;
	}

	public Boolean getInterfaceDisabled() {
		return interfaceDisabled;
	}

	public void setInterfaceDisabled(Boolean interfaceDisabled) {
		this.interfaceDisabled = interfaceDisabled;
	}

	public String getInterfaceComment() {
		return interfaceComment;
	}

	public void setInterfaceComment(String interfaceComment) {
		this.interfaceComment = interfaceComment;
	}

	public String getInterfaceInterface() {
		return interfaceInterface;
	}

	public void setInterfaceInterface(String interfaceInterface) {
		this.interfaceInterface = interfaceInterface;
	}

	public String getInterfaceNetwork() {
		return interfaceNetwork;
	}

	public void setInterfaceNetwork(String interfaceNetwork) {
		this.interfaceNetwork = interfaceNetwork;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public Long getVlanId() {
		return vlanId;
	}

	public void setVlanId(Long vlanId) {
		this.vlanId = vlanId;
	}

	public Boolean getEsActivo() {
		return esActivo;
	}

	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}
	
}
