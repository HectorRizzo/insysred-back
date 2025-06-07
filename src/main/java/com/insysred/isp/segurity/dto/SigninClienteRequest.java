package com.insysred.isp.segurity.dto;

import com.insysred.isp.enums.TipoDocumento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninClienteRequest {
    private TipoDocumento tipoDocumento;
    private String identificacion;
}
