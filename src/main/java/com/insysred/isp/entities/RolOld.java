package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "roles_old")
@Data
public class RolOld implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name="descripcion", length = 500)
    private String descripcion;
}
