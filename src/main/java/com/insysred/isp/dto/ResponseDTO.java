package com.insysred.isp.dto;

import lombok.Data;

@Data
public class ResponseDTO {
    private String message;
    private Integer status;
    private Object data;

    public ResponseDTO(String message, Integer status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
