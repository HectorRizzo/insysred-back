package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "horario_visita")
@Data
public class HorarioVisita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hora_inicio", length = 5)
    private String horaInicio;

    @Column(name = "hora_fin", length = 5)
    private String horaFin;

    @Column(name = "is_active")
    private Boolean isActive;
}
