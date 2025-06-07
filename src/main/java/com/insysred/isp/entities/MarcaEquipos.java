package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "marca_equipos")
@Data
public class MarcaEquipos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_marca", length = 50)
    private String nombreMarca;

    @Column(name = "nombre_modelo", length = 50)
    private String nombreModelo;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
}
