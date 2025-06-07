package com.insysred.isp.entities;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "contrato")
@Data
public class Contrato implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "num_contrato")
  private Long numContrato;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "servidor_id")
  private Routers servidor;

  @ManyToOne
  @JoinColumn(name = "plan_id")
  private Planes plan;

  @Column(name = "longitud", length = 100)
  private String longitud;

  @Column(name = "latitud", length = 100)
  private String latitud;

  @Column(name = "ubicacion", length = 250)
  private String ubicacion;

  @Column(name = "referencia", length = 250)
  private String referencia;

  @Column(name = "ip", length = 100)
  private String ip;

  @Column(name = "mac", length = 100)
  private String mac;

  @Column(name = "fecha_crea")
  private Instant fechaCrea;

  @Column(name = "feha_modifica")
  private Instant fechaModifica;

  @Column(name = "fecha_contrato")
  private Instant fechaContrato;

  @Column(name = "feha_instala")
  private Instant fechaInstala;

  @Column(name = "fecha_fin")
  private Instant fechaFin;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "sucursal_id")
  private Sucursales sucursales;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoContrato estadoContrato;

  @Column(name = "envio_microtik")
  private Boolean envioMicrotick = false;
}
