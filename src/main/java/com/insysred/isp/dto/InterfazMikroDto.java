package com.insysred.isp.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class InterfazMikroDto implements Serializable {
	@SerializedName("mac-address")
	private String macAddress;

	@SerializedName("l2mtu")
	private String l2mtu;

	@SerializedName("fp-tx-packet")
	private String fpTxPacket;

	@SerializedName("fp-tx-byte")
	private String fpTxByte;

	@SerializedName("tx-queue-drop")
	private String txQueueDrop;

	@SerializedName(".id")
	private String id;

	@SerializedName("max-l2mtu")
	private String maxL2mtu;

	@SerializedName("type")
	private String type;

	@SerializedName("rx-packet")
	private String rxPacket;

	@SerializedName("default-name")
	private String defaultName;

	@SerializedName("fp-rx-packet")
	private String fpRxPacket;

	@SerializedName("mtu")
	private String mtu;

	@SerializedName("fp-rx-byte")
	private String fpRxByte;

	@SerializedName("running")
	private String running;

	@SerializedName("tx-byte")
	private String txByte;

	@SerializedName("actual-mtu")
	private String actualMtu;

	@SerializedName("link-downs")
	private String linkDowns;

	@SerializedName("name")
	private String name;

	@SerializedName("disabled")
	private String disabled;

	@SerializedName("rx-byte")
	private String rxByte;

	@SerializedName("tx-packet")
	private String txPacket;

	private RoutersDto router;

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getL2mtu() {
		return l2mtu;
	}

	public void setL2mtu(String l2mtu) {
		this.l2mtu = l2mtu;
	}

	public String getFpTxPacket() {
		return fpTxPacket;
	}

	public void setFpTxPacket(String fpTxPacket) {
		this.fpTxPacket = fpTxPacket;
	}

	public String getFpTxByte() {
		return fpTxByte;
	}

	public void setFpTxByte(String fpTxByte) {
		this.fpTxByte = fpTxByte;
	}

	public String getTxQueueDrop() {
		return txQueueDrop;
	}

	public void setTxQueueDrop(String txQueueDrop) {
		this.txQueueDrop = txQueueDrop;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaxL2mtu() {
		return maxL2mtu;
	}

	public void setMaxL2mtu(String maxL2mtu) {
		this.maxL2mtu = maxL2mtu;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRxPacket() {
		return rxPacket;
	}

	public void setRxPacket(String rxPacket) {
		this.rxPacket = rxPacket;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public String getFpRxPacket() {
		return fpRxPacket;
	}

	public void setFpRxPacket(String fpRxPacket) {
		this.fpRxPacket = fpRxPacket;
	}

	public String getMtu() {
		return mtu;
	}

	public void setMtu(String mtu) {
		this.mtu = mtu;
	}

	public String getFpRxByte() {
		return fpRxByte;
	}

	public void setFpRxByte(String fpRxByte) {
		this.fpRxByte = fpRxByte;
	}

	public String getRunning() {
		return running;
	}

	public void setRunning(String running) {
		this.running = running;
	}

	public String getTxByte() {
		return txByte;
	}

	public void setTxByte(String txByte) {
		this.txByte = txByte;
	}

	public String getActualMtu() {
		return actualMtu;
	}

	public void setActualMtu(String actualMtu) {
		this.actualMtu = actualMtu;
	}

	public String getLinkDowns() {
		return linkDowns;
	}

	public void setLinkDowns(String linkDowns) {
		this.linkDowns = linkDowns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getRxByte() {
		return rxByte;
	}

	public void setRxByte(String rxByte) {
		this.rxByte = rxByte;
	}

	public String getTxPacket() {
		return txPacket;
	}

	public void setTxPacket(String txPacket) {
		this.txPacket = txPacket;
	}

	public RoutersDto getRouter() {
		return router;
	}

	public void setRouter(RoutersDto router) {
		this.router = router;
	}

}
