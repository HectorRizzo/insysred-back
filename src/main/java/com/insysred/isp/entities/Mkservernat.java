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
@Table(name = "mkservernat")
@Data
public class Mkservernat implements Serializable {
    
    @Id
    @Column(name = "id_servernat")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServernat;

    @Column(name = "jump_target", length = 256)
    private String  jumpTarget;

    @Column(name = "nat_chain", length = 250)
    private String  natChain;

    @Column(name = "nat_log")
    private String natLog;

    @Column(name = "log_prefix", length = 250)
    private String logPrefix;

    @Column(name = "bytes")
    private Long bytes;

    @Column(name = "invalid")
    private String invalid;

    @Column(name = "nat_id", length = 10)
    private String natId;

    @Column(name = "nat_action", length = 50)
    private String natAction;

    @Column(name = "nat_dynamic")
    private Boolean natDynamic;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "nat_comment", length = 256)
    private String natComment;

    @Column(name = "packets")
    private Long packets;

    @Column(name = "to_ports")
    private Long to_ports;

    @Column(name = "src_address_list")
    private String srcAddressList;

    @Column(name = "dst_port")
    private Long dstPort;

    @Column(name = "dst_address_list", length = 50)
    private String dstAddress_list;

    @Column(name = "protocol", length = 50)
    private String protocol;
}
