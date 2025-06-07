package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Entity
@Table(name = "referencia")
@Data
public class Referencia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parentesco", length = 100)
    private String parentesco;

    @Column(name = "apellidos", length = 50)
    private String apellidos;

    @Column(name = "nombres", length = 50)
    private String nombres;

    @Column(name = "telef_fijo", length = 9)
    private String telfFijo;

    @Column(name = "telef_movil", length = 10)
    private String telfMovil;

    @OneToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
    private Provincia provincia;

    @ManyToOne
    @JoinColumn(name = "canton_id", referencedColumnName = "id")
    private Canton canton;

    @Column(name = "direccion", length = 250)
    private String direccion;
}
