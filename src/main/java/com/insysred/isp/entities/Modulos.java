package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "modulos")
@Data
public class Modulos implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 50)
    private String nombreModulo;

    @Column(name = "padre")
    private Long padre;

    @Column(name = "ruta")
    private String ruta;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
}
