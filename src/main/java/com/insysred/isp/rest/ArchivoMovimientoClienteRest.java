package com.insysred.isp.rest;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import com.insysred.isp.dto.*;
import org.apache.commons.lang3.ObjectUtils;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.insysred.isp.entities.ArchivoMovimientoCliente;
import com.insysred.isp.enums.TipoExtension;
import com.insysred.isp.service.declare.ArchivoMovimientoClienteService;

@RestController
@RequestMapping("/api")
public class ArchivoMovimientoClienteRest {
	@Autowired
	private ArchivoMovimientoClienteService archivoMovimientoClienteService;

	@GetMapping("/archivosMovCliente/contratos/{idCliente}")
	public ResponseEntity<List<ArchivoMovimientoClienteContratoDetDTO>> getContratosCliente(
			@PathVariable Long idCliente) {
		try {
			return new ResponseEntity<>(archivoMovimientoClienteService.obtenerContratosCliente(idCliente),
					HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/archivosMovCliente/ultimos/{idCliente}")
	public ResponseEntity<List<ArchivoMovimientoClienteUltimosDTO>> getArchivosMovimientoClienteUltimos(
			@PathVariable Long idCliente) {
		try {
			return new ResponseEntity<>(
					archivoMovimientoClienteService.obtenerArchivosMovimientoClienteUltimos(idCliente), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/archivosMovCliente")
	public ResponseEntity<ResponseDTO> postArchivoMovimientoCliente(
			@RequestParam(name = "archivo", required = false) MultipartFile archivo,
			@RequestParam(name = "idCliente", required = false) Long idCliente,
			@RequestParam(name = "idContrato", required = false) Long idContrato) {

		try {
			if (ObjectUtils.isEmpty(archivo)) {
				throw new Exception("El campo archivo es obligatorio.");
			}

			if (ObjectUtils.isEmpty(idCliente)) {
				throw new Exception("El campo idCliente es obligatorio.");
			}

			if (ObjectUtils.isEmpty(idContrato)) {
				throw new Exception("El campo idContrato es obligatorio.");
			}

			String contentType = MediaType.parseMediaType(archivo.getContentType()).toString();

			if (!contentType.equals(TipoExtension.PNG.getContentType())
					&& !contentType.equals(TipoExtension.JPG.getContentType())
					&& !contentType.equals(TipoExtension.JPEG.getContentType())) {
				throw new NoSuchElementException("El formato del documento no es valido.");
			}

			archivoMovimientoClienteService.uploadArchivoMovimientoCliente(idCliente, idContrato, archivo);

			return ResponseEntity.ok(new ResponseDTO("Archivo subido correctamente", 200, null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Error al subir el archivo", 400,
					e.getMessage()));
		}
	}

	@GetMapping("/archivosMovCliente")
	public ResponseEntity<Page<ArchivoMovimientoCliente>> getAllArchivosMovimientoCliente(
			@RequestParam(defaultValue = "0") int pagina, @RequestParam(defaultValue = "10") int tamanoPagina,
			@RequestParam(defaultValue = "") String filtro, @RequestParam(defaultValue = "") String fechaInicio,
			@RequestParam(defaultValue = "") String fechaFin) {
		try {
			PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina, Sort.by("id").descending());
			Instant insFechaInicio = ObjectUtils.isNotEmpty(fechaInicio) ? Instant.parse(fechaInicio) : null;
			Instant insFechaFin = ObjectUtils.isNotEmpty(fechaFin) ? Instant.parse(fechaFin) : null;
			Page<ArchivoMovimientoCliente> resultadoPaginado = archivoMovimientoClienteService
					.obtenerArchivosMovimientoCliente(paginaRequest, filtro, insFechaInicio, insFechaFin);
			return ResponseEntity.ok(resultadoPaginado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/archivosMovCliente/{id}")
	public ResponseEntity<ArchivoMovimientoClienteDTO> getArchivosMovimientoClienteId(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(archivoMovimientoClienteService.obtenerArchivoMovimientoClienteId(id),
					HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/archivosMovCliente/validar")
	public ResponseEntity<ExistenciaMovClienteXMovBancoDTO> validar(@RequestParam Long numeroComprobante) {
		try {
			return new ResponseEntity<>(
					archivoMovimientoClienteService.validarComprobanteMovClienteXMovBanco(numeroComprobante),
					HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/archivosMovCliente/conciliar")
	public ResponseEntity<ArchivoMovimientoClienteDTO> conciliar(
			@RequestBody ConciliarArchivoMovimientoClienteDTO validarArchivoMovimientoCliente) {
		try {
			return new ResponseEntity<>(
					archivoMovimientoClienteService.saveArchivoMovimientoCliente(validarArchivoMovimientoCliente),
					HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
