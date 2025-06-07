package com.insysred.isp.entities;

import com.insysred.isp.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "representante_legal")
public class RepresentanteLegal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_identificacion", length = 9)
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(name = "identificacion", length = 13)
    private String identificacion;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Column(name = "nombres", length = 100)
    private String nombres;

    @Column(name = "cargo", length = 100)
    private String cargo;

    @Column(name = "is_active", length = 100)
    private Boolean isActive;

}
