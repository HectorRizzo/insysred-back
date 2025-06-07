package com.insysred.isp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EmpleadosDTO implements Serializable{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String nombreCompleto;
    private String tipoIdentificacion;
    private String identificacion;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean activo;
    private String sexo;
    private Date fechaNacimiento;
    private String direccion;
    private String telefonoFijo;
    private String telefonoMovil;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date fechaIngreso;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date fechaSalida;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long idCargo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long idDepartamento;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long idJefe;

}
