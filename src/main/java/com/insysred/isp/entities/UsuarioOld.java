package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "usuarios_old")
@Data
public class UsuarioOld implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    @Column(name = "identificacion")
    private String identificacion;

    @Column(name = "nombres", length = 50)
    private String nombres;

    @Column(name = "apellidos", length = 50)
    private String apellidos;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private RolOld rolOld;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_sucursales",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sucursal_id", referencedColumnName = "id")
    )
    private List<Sucursales> sucursales;


}
