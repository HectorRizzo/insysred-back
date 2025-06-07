package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "interfaz_microtik")
public class InterfazMikro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_interfaz")
    private Long codInterfaz;

    @ManyToOne
    @JoinColumn(name = "router_id")
    private Routers router;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "l2mtu")
    private String l2mtu;

    @Column(name = "fp_tx_Packet")
    private String fpTxPacket;

    @Column(name = "fpTxByte")
    private String fpTxByte;

    @Column(name = "txQueueDrop")
    private String txQueueDrop;

    @Column(name = "id")
    private String id;

    @Column(name = "maxL2mtu")
    private String maxL2mtu;

    @Column(name = "type")
    private String type;

    @Column(name = "rxPacket")
    private String rxPacket;

    @Column(name = "defaultName")
    private String defaultName;

    @Column(name = "fpRxPacket")
    private String fpRxPacket;

    @Column(name = "mtu")
    private String mtu;

    @Column(name = "fpRxByte")
    private String fpRxByte;

    @Column(name = "running")
    private String running;

    @Column(name = "txByte")
    private String txByte;

    @Column(name = "actualMtu")
    private String actualMtu;

    @Column(name = "linkDowns")
    private String linkDowns;

    @Column(name = "name")
    private String name;

    @Column(name = "disabled")
    private String disabled;

    @Column(name = "rxByte")
    private String rxByte;

    @Column(name = "txPacket")
    private String txPacket;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
