package com.insysred.isp.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.insysred.isp.service.declare.JasperReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class JasperReportServiceImpl implements JasperReportService {

	@Override
	public File generarReporteJasper(String strPathJasperFile, String strNombreArchivo, String strExtension,
			Map<String, Object> mapParametros, Object data) throws Exception {
		try {
			String strPathCreacionTemporalArchivos = "/isp-tmp/";

			JasperReport jasReport = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream(strPathJasperFile));
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource((ArrayList<?>) data);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasReport, mapParametros, jrBeanCollectionDataSource);

			File archivoPdf = File.createTempFile(strNombreArchivo, "." + strExtension, new File(strPathCreacionTemporalArchivos));
			FileOutputStream os = new FileOutputStream(archivoPdf);
			JasperExportManager.exportReportToPdfStream(jasperPrint, os);

			try {
				os.close();
				mapParametros.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return archivoPdf;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
