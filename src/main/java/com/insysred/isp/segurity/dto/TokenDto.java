package com.insysred.isp.segurity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
public class TokenDto implements Serializable {
  private String access_token;
  private String token_type;
  private Integer expires_in;
  private Long exp;
  private String refresh_token;
  private Long idUsuario;
  private Long idEmpleado;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean esPrimerIngreso;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String username;
}
