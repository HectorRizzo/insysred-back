package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RuleDto implements Serializable {
    private String nombre;
    private String adress;
}
