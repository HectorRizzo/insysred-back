package com.insysred.isp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.insysred.isp.enums.Sexo;
import com.insysred.isp.enums.TipoDocumento;
import com.insysred.isp.validation.ValidSexo;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ClienteDto implements Serializable {
    private Long id;
    private TipoDocumento tipoDocumento;
    private String identificacion;

    @ValidSexo
    private Sexo sexo;

    private Instant fechaNace;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String apellidos;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String nombres;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String razonSocial;
    private String email;
    private String telfFijo;
    private String telfCelular;
    private String fileIdentificacion;
    private String filePlanilla;
    private String ubicacion;
    private String referenciaUbicacion;
    private String longitud;
    private String latitud;
    private ReferenciaDTO referencia;
    private ProvinciaDto provincia;
    private CantonDto canton;

}
