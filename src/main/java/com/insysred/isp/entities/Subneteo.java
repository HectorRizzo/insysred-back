package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "subneteo")
public class Subneteo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ip_base")
    private String ipBase;

    @Column(name = "interfaz")
    private String interfaz;

    @ManyToOne
    @JoinColumn(name = "router_id")
    private Routers router;

    @ManyToOne
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;

    @Column(name = "ip")
    private String ip;
}
