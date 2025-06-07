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
@Table(name = "mkserverrules")
@Data
public class Mkserverrules implements Serializable {

	@Id
	@Column(name = "id_serverrule")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idServerrule;

	@Column(name = "rule_chain", length = 250)
	private String ruleChain;

	@Column(name = "src_address_list", length = 50)
	private String srcAddress_list;

	@Column(name = "rule_log")
	private Boolean ruleLog;

	@Column(name = "log_prefix", length = 50)
	private String logPrefix;

	@Column(name = "bytes")
	private Long bytes;

	@Column(name = "invalid")
	private Boolean invalid;

	@Column(name = "rule_id", length = 250)
	private String ruleId;

	@Column(name = "rule_action", length = 250)
	private String ruleAction;

	@Column(name = "rule_dynamic")
	private Boolean ruleDynamic;

	@Column(name = "disabled")
	private Boolean disabled;

	@Column(name = "rule_comment", length = 250)
	private String ruleComment;

	@Column(name = "packets")
	private Long packets;

	@Column(name = "dst_address_list", length = 250)
	private String dsAddressList;

	@Column(name = "jump_target", length = 250)
	private String jumpTarget;

	@Column(name = "dst_port", length = 250)
	private String dstPort;

	@Column(name = "protocol", length = 50)
	private String protocol;

	@Column(name = "connection_state", length = 10)
	private String connectionState;

	@Column(name = "in_interface", length = 50)
	private String inInterface;

	@Column(name = "icmp_options", length = 50)
	private String icmpOptions;

	@Column(name = "address_list_timeout", length = 50)
	private String addressListTimeout;

	@Column(name = "address_list", length = 250)
	private String addressList;

	@Column(name = "connection_limit", length = 250)
	private String connectionLimit;

	@Column(name = "rule_limit", length = 250)
	private String ruleLimit;

	@Column(name = "dst_limit", length = 250)
	private String dstLimit;

}
