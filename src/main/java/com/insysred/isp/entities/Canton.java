package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "canton")
@Data
public class Canton implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;
}
