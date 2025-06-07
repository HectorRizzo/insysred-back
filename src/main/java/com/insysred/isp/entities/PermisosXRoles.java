package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "permisos_x_roles")
public class PermisosXRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "id_modulo")
    private Long idModulo;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
}
