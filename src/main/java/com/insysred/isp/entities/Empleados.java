package com.insysred.isp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "empleados")
@Data
public class Empleados implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono_fijo")
    private String telefonoFijo;

    @Column(name = "telefono_movil")
    private String telefonoMovil;

    @Column(name = "correo")
    private String correo;

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Column(name = "fecha_salida")
    private Date fechaSalida;

    @Column(name = "id_cargo")
    private Long idCargo;

    @Column(name = "id_departamento")
    private Long idDepartamento;

    @Column(name = "id_jefe")
    private Long idJefe;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @Column(name = "id_usuario_ingreso")
    private Long idUsuarioIngreso;

    @Column(name = "id_usuario_modificacion")
    private Long idUsuarioModificacion;

    @ManyToOne
    @JoinColumn(name = "id_cargo", insertable = false, updatable = false)
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "id_departamento", insertable = false, updatable = false)
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "id_jefe", insertable = false, updatable = false)
    @JsonBackReference
    private Empleados jefe;


}
