package com.insysred.isp.dto;

import com.insysred.isp.enums.TipoDocumento;
import lombok.Data;

import java.io.Serializable;

@Data
public class RepresentanteLegalDto implements Serializable {
    private Long id;
    private TipoDocumento tipoDocumento;
    private String identificacion;
    private String apellidos;
    private String nombres;
    private String cargo;

}
