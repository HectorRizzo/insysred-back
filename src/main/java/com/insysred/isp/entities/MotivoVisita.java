package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "motivo_visita")
@Data
public class MotivoVisita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "motivo", length = 50)
    private String motivo;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "is_activo")
    private Boolean isActive;

}
