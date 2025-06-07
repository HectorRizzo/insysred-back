package com.insysred.isp.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class DocumentoFileDto implements Serializable {
    private MultipartFile cedula;
    private MultipartFile planilla;
}
