package com.insysred.isp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
@Data
public class SucursalesXAsignarDTO implements Serializable {
    private Long id;
    private Boolean checked;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nombre;
}
