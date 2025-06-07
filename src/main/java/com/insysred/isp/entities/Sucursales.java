package com.insysred.isp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sucursales")
@Data
public class Sucursales implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nombre", length = 100)
  private String nombre;

  @Column(name = "direccion")
  private String direccion;

  @Column(name = "establecimiento", length = 3)
  private String establecimiento;

  @Column(name = "punto_emision", length = 3)
  private String puntoEmision;

  @Column(name = "secuencial", length = 9)
  private String secuencial;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "empresa_id")
  private Empresa empresa;

  @ManyToMany(mappedBy = "sucursales")
  @JsonIgnore
  private List<UsuarioOld> usuarioOlds;

  @ManyToOne
  @JoinColumn(name = "canton_id")
  private Canton canton;


}
