package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MkserverrulesDto implements Serializable {
	private Long idServerrule;
	private String ruleChain;
	private String srcAddress_list;
	private Boolean ruleLog;
	private String logPrefix;
	private Long bytes;
	private Boolean invalid;
	private String ruleId;
	private String ruleAction;
	private Boolean ruleDynamic;
	private Boolean disabled;
	private String ruleComment;
	private Long packets;
	private String dsAddressList;
	private String jumpTarget;
	private String dstPort;
	private String protocol;
	private String connectionState;
	private String inInterface;
	private String icmpOptions;
	private String addressListTimeout;
	private String addressList;
	private String connectionLimit;
	private String ruleLimit;
	private String dstLimit;
}
