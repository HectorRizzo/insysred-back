package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "empresa")
@Data
public class Empresa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ruc", length = 13, nullable = false)
    private String ruc;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "nombre_comercial", length = 100)
    private String nombreComercial;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "representante_id")
    private RepresentanteLegal representanteLegal;

    @Column(name = "telf_fijo")
    private String telefonoFijo;


    @Column(name = "telf_movil", length = 10)
    private String telefonoMovil;

    @Column(name = "correo", length = 50)
    private String correo;

    @Column(name = "logo")
    @Lob
    private byte[] logo;
}
