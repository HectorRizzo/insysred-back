package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Entity
@Table(name = "ubicacion")
@Data
public class Ubicacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "georeferencia")
    private String georeferencia;

}
