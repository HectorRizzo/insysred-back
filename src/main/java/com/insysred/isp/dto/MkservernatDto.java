package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MkservernatDto implements Serializable {
	private Long idServernat;
	private String jumpTarget;
	private String natChain;
	private String natLog;
	private String logPrefix;
	private Long bytes;
	private String invalid;
	private String natId;
	private String natAction;
	private Boolean natDynamic;
	private Boolean disabled;
	private String natComment;
	private Long packets;
	private Long to_ports;
	private String srcAddressList;
	private Long dstPort;
	private String dstAddress_list;
	private String protocol;
}
