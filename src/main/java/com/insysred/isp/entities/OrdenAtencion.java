package com.insysred.isp.entities;

import com.insysred.isp.enums.EstadoOrden;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "orden_atencion")
@Data
public class OrdenAtencion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_trabajo_id")
    private OrdenTrabajo ordenTrabajo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioOld tecnico;

    @Column(name = "fecha_aitiende")
    private Instant fechaAtencion;

    @Column(name = "estado_orden", length = 50)
    @Enumerated(EnumType.STRING)
    private EstadoOrden estadoOrden;

    @Column(name = "detalle")
    private String detalle;

}
