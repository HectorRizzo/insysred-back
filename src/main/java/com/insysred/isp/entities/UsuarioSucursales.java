package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "usuario_sucursales")
@Data
public class UsuarioSucursales implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "sucursal_id")
    private Long sucursalId;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

}
