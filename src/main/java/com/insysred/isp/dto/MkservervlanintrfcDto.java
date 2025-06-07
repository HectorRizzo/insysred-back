package com.insysred.isp.dto;

import lombok.Data;

@Data
public class MkservervlanintrfcDto {

	private Long id;
	private String interfaceAddress;
	private String interfaceActualInterface;
	private Boolean interfaceInvalid;
	private String interfaceId;
	private Boolean interfaceDynamic;
	private Boolean interfaceDisabled;
	private String interfaceComment;
	private String interfaceInterface;
	private String interfaceNetwork;
	private Long serverId;
	private Long vlanId;
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
