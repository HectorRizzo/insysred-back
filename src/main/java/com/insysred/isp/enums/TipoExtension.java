package com.insysred.isp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TipoExtension {
	PDF("application/pdf", "pdf"), 
	XLS("application/vnd.ms-excel", "xls"),
	XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx"),
	PNG("image/png", "png"),
	JPG("image/jpg", "jpg"),
	JPEG("image/jpeg", "jpeg");
	
	private final String contentType;
	private final String extension;
}
