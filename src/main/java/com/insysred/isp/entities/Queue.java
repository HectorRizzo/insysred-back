package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "queue")
public class Queue implements Serializable {
    @Id
    @Column(name = ".id")
    private String id;

    @Column(name = "parent")
    private String parent;

    @Column(name = "queued-packets")
    private String queuedPackets;

    @Column(name = "burst-time")
    private String burstTime;

    @Column(name = "dropped")
    private String dropped;

    @Column(name = "packet-rate")
    private String packetRate;

    @Column(name = "priority")
    private String priority;

    @Column(name = "queued-bytes")
    private String queuedBytes;

    @Column(name = "bucket-size")
    private String bucketSize;

    @Column(name = "packets")
    private String packets;

    @Column(name = "limit-at")
    private String limitAt;

    @Column(name = "burst-limit")
    private String burstLimit;

    @Column(name = "burst-threshold")
    private String burstThreshold;

    @Column(name = "rate")
    private String rate;

    @Column(name = "bytes")
    private String bytes;

    @Column(name = "name")
    private String name;

    @Column(name = "invalid")
    private String invalid;

    @Column(name = "packet-mark")
    private String packetMark;

    @Column(name = "disabled")
    private String disabled;

    @Column(name = "max-limit")
    private String maxLimit;

    @Column(name = "queue")
    private String queue;

    @ManyToOne
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;

}
