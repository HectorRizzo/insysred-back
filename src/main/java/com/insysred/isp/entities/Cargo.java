package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "cargo")
@Data
public class Cargo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "id_departamento")
    private Long idDepartamento;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_departamento", insertable = false, updatable = false)
    private Departamento departamento;
}
