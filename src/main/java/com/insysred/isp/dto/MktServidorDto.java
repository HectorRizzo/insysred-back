package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class MktServidorDto implements Serializable {
	private Long serverid;
    private Instant fechacrea;
    private Long idusuariocrea;
    private Instant fechaactualiza;
    private Long idusuarioactualiza;
    private Boolean habilitado;
    private Long sucursalid;
    private String nombremkt;
    private String usuario;
    private String ip;
    private String puerto;
    private String gateway;
    private String nombrearp;
}
