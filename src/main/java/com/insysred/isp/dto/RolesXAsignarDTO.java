package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolesXAsignarDTO implements Serializable {
    private Long id;
    private Boolean checked;
}
