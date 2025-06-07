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

import com.insysred.isp.dto.AnularFacturaDto;
import com.insysred.isp.dto.AplicarDescuentoFacturaDto;
import com.insysred.isp.dto.CambioEstadoFacturaDto;
import com.insysred.isp.dto.FacturaDTO;
import com.insysred.isp.entities.Factura;
import com.insysred.isp.service.declare.FacturaService;

@RestController
@RequestMapping("/api")
public class FacturaRest {
	@Autowired
	private FacturaService facturaService;

	@GetMapping("/factura")
	public ResponseEntity<Page<Factura>> getAllFacturas(
			@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanoPagina,
			@RequestParam(defaultValue = "") String filtro,
			@RequestParam(defaultValue = "") String fechaInicio,
			@RequestParam(defaultValue = "") String fechaFin,
			@RequestParam(defaultValue = "") String estado) {
		try {
			PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina, Sort.by("id").descending());
			Instant insFechaInicio = ObjectUtils.isNotEmpty(fechaInicio) ? Instant.parse(fechaInicio) : null;
			Instant insFechaFin = ObjectUtils.isNotEmpty(fechaFin) ? Instant.parse(fechaFin) : null;
			Page<Factura> resultadoPaginado = facturaService.obtenerFacturas(paginaRequest, filtro, insFechaInicio, insFechaFin, estado);
			return ResponseEntity.ok(resultadoPaginado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("/factura/{id}")
	public ResponseEntity<FacturaDTO> getFactura(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(facturaService.obtenerFacturaId(id), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/factura/cmb_estado")
	public ResponseEntity<FacturaDTO> cmbEstado(@RequestBody CambioEstadoFacturaDto cambioEstadoFacturaDto) {
		try {
			return new ResponseEntity<>(facturaService.cmbEstadoFactura(cambioEstadoFacturaDto), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/factura/anular")
	public ResponseEntity<FacturaDTO> cmbEstado(@RequestBody AnularFacturaDto anularFacturaDto) {
		try {
			return new ResponseEntity<>(facturaService.anularFactura(anularFacturaDto), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/factura/aplicarDescuento")
	public ResponseEntity<FacturaDTO> cmbEstado(@RequestBody AplicarDescuentoFacturaDto aplicarDescuentoFacturaDto) {
		try {
			return new ResponseEntity<>(facturaService.aplicarDescuento(aplicarDescuentoFacturaDto), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("/factura/generarRecibo")
	public ResponseEntity<?> generarRecibo(@RequestParam() Long idRecibo) {
		try {
			return facturaService.generarReporteRecibo(idRecibo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
