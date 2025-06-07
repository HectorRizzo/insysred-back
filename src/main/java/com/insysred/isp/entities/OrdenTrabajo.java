package com.insysred.isp.entities;

import com.insysred.isp.enums.EstadoOrden;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@Entity
@Table(name = "orden_trabajo_cab")
public class OrdenTrabajo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private Long codigo;

    @Column(name = "numero_orden")
    private Long numeroOrden;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursales sucursal;

    @ManyToOne
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente codigoCliente;

    @Column(name = "fecha_visita")
    private Date fechaVisita;

    @ManyToOne
    @JoinColumn(name = "hora_visita_id")
    private HorarioVisita horaVisita;

    @Column(name = "persona_contacto", length = 100)
    private String personaContacto;

    @Column(name = "celular_contacto", length = 10)
    private String celularContacto;

    @Column(name = "estado_orden", length = 50)
    @Enumerated(EnumType.STRING)
    private EstadoOrden estadoOrden;

    @ManyToOne
    @JoinColumn(name = "motivo_id")
    private MotivoVisita motivo;

    @Column(name = "es_activo")
    private Boolean esActivo;

    @Column(name = "fecha_crea")
    private Instant fechaCrea;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "tecnicos_array")
    private String tecnicos;

    @Column(name = "refererncia_direccion")
     private String referenciaDireccion;

}
