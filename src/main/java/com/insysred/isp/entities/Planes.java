package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "planes")
public class Planes implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "descripcion")
  private String descripcion;

  @Column(name = "megabytes")
  private Integer megabytes;

  @Column(name = "price")
  private Double price;

  @Column(name = "status")
  private Boolean status;

  @ManyToOne
  @JoinColumn(name = "sucursal_id")
  private Sucursales sucursales;

  @Column(name = "envio_microtik")
  private Boolean envioMicrotick = false;
}
