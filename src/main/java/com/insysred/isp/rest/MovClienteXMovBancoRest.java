package com.insysred.isp.rest;

import java.time.Instant;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insysred.isp.dto.ArchivoMovimientoClienteDTO;
import com.insysred.isp.dto.ConciliarArchivoMovimientoClienteDTO;
import com.insysred.isp.entities.MovClienteXMovBanco;
import com.insysred.isp.service.declare.MovClienteXMovBancoService;

@RestController
@RequestMapping("/api")
public class MovClienteXMovBancoRest {
	@Autowired
	private MovClienteXMovBancoService movClienteXMovBancoService;

	@GetMapping("/conciliacion")
	public ResponseEntity<Page<MovClienteXMovBanco>> getAllConciliacion(
			@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanoPagina,
			@RequestParam(defaultValue = "") Long filtroBanco,
			@RequestParam(defaultValue = "") String filtroCliente,
			@RequestParam(defaultValue = "") String filtroComprobante,
			@RequestParam(defaultValue = "") String fechaInicio,
			@RequestParam(defaultValue = "") String fechaFin,
			@RequestParam(defaultValue = "") String estado) {
		try {
			PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina, Sort.by("id").descending());
			Instant insFechaInicio = ObjectUtils.isNotEmpty(fechaInicio) ? Instant.parse(fechaInicio) : null;
			Instant insFechaFin = ObjectUtils.isNotEmpty(fechaFin) ? Instant.parse(fechaFin) : null;
			Page<MovClienteXMovBanco> resultadoPaginado = movClienteXMovBancoService.obtenerConciliacion(paginaRequest,
					filtroCliente, filtroComprobante, filtroBanco, insFechaInicio, insFechaFin, estado);
			return ResponseEntity.ok(resultadoPaginado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/conciliacion/manual/{id}")
	public ResponseEntity<?> conciliacionManual(@PathVariable Long id) {
		try {
			movClienteXMovBancoService.conciliacionManual(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
