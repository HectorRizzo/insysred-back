package com.insysred.isp.service.declare;

import java.io.File;
import java.util.Map;

public interface JasperReportService {

	public File generarReporteJasper(String strPathJasperFile, String strNombreArchivo, String strExtension,
			Map<String, Object> mapParametros, Object data) throws Exception;
}
