package com.insysred.isp.entities;

import com.insysred.isp.enums.Pais;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Entity
@Table(name = "provincia")
@Data
public class Provincia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pais", length = 50)
    @Enumerated(EnumType.STRING)
    private Pais pais;

    @Column(name = "nombre", length = 50)
    private String nombre;

}
