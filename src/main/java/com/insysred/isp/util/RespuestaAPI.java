package com.insysred.isp.util;

import com.insysred.isp.enums.TipoRespuesta;
import lombok.Data;

import java.io.Serializable;

@Data
public class RespuestaAPI implements Serializable {
    private TipoRespuesta tipoRespuesta;
    private String textMensaje;
}
