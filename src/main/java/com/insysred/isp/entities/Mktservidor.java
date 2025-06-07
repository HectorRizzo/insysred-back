package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "mktservidores")
@Data
public class Mktservidor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serverid;

    @Column(name = "fechacrea")
    private Instant fechacrea;
    
    @Column(name = "idusuariocrea")
    private Long idusuariocrea;
    
    @Column(name = "fechaactualiza")
    private Instant fechaactualiza;
    
    @Column(name = "idusuarioactualiza")
    private Long idusuarioactualiza;
    
    @Column(name = "habilitado")
    private Boolean habilitado;
    
    @Column(name = "sucursalid")
    private Long sucursalid;
    
    @Column(name = "nombremkt", length = 250)
    private String nombremkt;

    @Column(name = "usuario", length = 50)
    private String usuario;
    
    @Column(name = "ip", length = 100)
    private String ip;
    
    @Column(name = "puerto", length = 100)
    private String puerto;
    
    @Column(name = "gateway", length = 100)
    private String gateway;
    
    @Column(name = "nombrearp", length = 100)
    private String nombrearp;
    
}
