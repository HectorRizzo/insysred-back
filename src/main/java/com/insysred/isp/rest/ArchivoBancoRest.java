package com.insysred.isp.rest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.insysred.isp.dto.ArchivoBancoDTO;
import com.insysred.isp.dto.DetalleArchivoBancoDTO;
import com.insysred.isp.entities.ArchivoBanco;
import com.insysred.isp.enums.EstadoCarga;
import com.insysred.isp.enums.TipoExtension;
import com.insysred.isp.service.declare.ArchivoBancoService;

@RestController
@RequestMapping("/api")
public class ArchivoBancoRest {
	@Autowired
	private ArchivoBancoService archivoConciliacionService;

	@GetMapping("/archivosConcilacion")
	public ResponseEntity<Page<ArchivoBanco>> getAllArchivosConciliacion(
			@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanoPagina,
			@RequestParam(defaultValue = "") String filtro,
			@RequestParam(defaultValue = "") String fechaInicio,
			@RequestParam(defaultValue = "") String fechaFin) {
		try {
			PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina, Sort.by("id").descending());
			Instant insFechaInicio = ObjectUtils.isNotEmpty(fechaInicio) ? Instant.parse(fechaInicio) : null;
			Instant insFechaFin = ObjectUtils.isNotEmpty(fechaFin) ? Instant.parse(fechaFin) : null;
			Page<ArchivoBanco> resultadoPaginado = archivoConciliacionService
					.obtenerArchivosConciliacion(paginaRequest, filtro, insFechaInicio, insFechaFin);
			return ResponseEntity.ok(resultadoPaginado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/archivosConcilacion/{id}")
	public ResponseEntity<ArchivoBancoDTO> getArchivoConciliacion(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(archivoConciliacionService.obtenerArchivoConciliacionId(id), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/archivosConcilacion")
	public ResponseEntity<ArchivoBancoDTO> postArchivoXls(@RequestParam(name = "excel", required = false) MultipartFile excel,
			@RequestParam(name = "idTipoBanco", required = false) Long idTipoBanco) {

		try {
			if (ObjectUtils.isEmpty(excel)) {
				throw new NoSuchElementException("El documento excel es obligatorio.");
			}
			if (ObjectUtils.isEmpty(idTipoBanco)) {
				throw new NoSuchElementException("El campo idTipoBanco es obligatorio.");
			}

			String filename = excel.getOriginalFilename();
			String contentType = MediaType.parseMediaType(excel.getContentType()).toString();
			if (!contentType.equals(TipoExtension.XLS.getContentType()) && !contentType.equals(TipoExtension.XLSX.getContentType())) {
				throw new NoSuchElementException("El documento excel es obligatorio.");
			}
			
			XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);

			List<DetalleArchivoBancoDTO> detalleArchivo = new ArrayList<DetalleArchivoBancoDTO>();

			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = sheet.getRow(i);
				DetalleArchivoBancoDTO detalle = new DetalleArchivoBancoDTO();
				XSSFCell fecha = row.getCell(0);
				XSSFCell documento = row.getCell(1);
				XSSFCell valor = row.getCell(2);
				XSSFCell referencia = row.getCell(3);

				if (ObjectUtils.isNotEmpty(fecha) && fecha.getCellType() == CellType.NUMERIC
						&& DateUtil.isCellDateFormatted(fecha)) {
					detalle.setFecha(fecha.getDateCellValue().toInstant());
				}
				if (ObjectUtils.isNotEmpty(documento) && documento.getCellType() == CellType.NUMERIC) {
					detalle.setDocumento((long) documento.getNumericCellValue());
				}
				if (ObjectUtils.isNotEmpty(valor) && valor.getCellType() == CellType.NUMERIC) {
					detalle.setValor(valor.getNumericCellValue());
				}
				if (ObjectUtils.isNotEmpty(referencia)) {
					detalle.setReferencia(referencia.toString());
				}

				detalleArchivo.add(detalle);
			}
			workbook.close();

			ArchivoBancoDTO archivo = new ArchivoBancoDTO();
			archivo.setNombre(filename);
			archivo.setFechaInicioCarga(Instant.now());
			archivo.setRegistrosExito(0);
			archivo.setRegistrosError(0);
			archivo.setRegistrosRepetido(0);
			archivo.setRegistrosTotal(detalleArchivo.size());
			archivo.setEstadoCarga(EstadoCarga.INICIO);

			return new ResponseEntity<>(archivoConciliacionService.saveArchivoConciliacion(archivo, detalleArchivo,idTipoBanco),
					HttpStatus.OK);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
