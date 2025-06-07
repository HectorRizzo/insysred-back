package com.insysred.isp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "usuario_roles")
@Data
public class UsuariosRoles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_usuario")
    private Long usuarioId;
    @Column(name = "id_rol")
    private Long rolId;
    @Column(name = "id_sucursal")
    private Long sucursalId;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;
    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
}
