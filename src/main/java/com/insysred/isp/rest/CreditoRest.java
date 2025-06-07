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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insysred.isp.dto.CreditoDTO;
import com.insysred.isp.entities.Credito;
import com.insysred.isp.service.declare.CreditoService;

@RestController
@RequestMapping("/api")
public class CreditoRest {
	@Autowired
	private CreditoService creditoService;

	@GetMapping(value = "/credito", produces = "application/json")
	public ResponseEntity<Page<Credito>> getAllFacturas(@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanoPagina, @RequestParam(defaultValue = "") String filtro,
			@RequestParam(defaultValue = "") String fechaInicio, @RequestParam(defaultValue = "") String fechaFin,
			@RequestParam(defaultValue = "") String estado) {
		try {
			PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina, Sort.by("id").descending());
			Instant insFechaInicio = ObjectUtils.isNotEmpty(fechaInicio) ? Instant.parse(fechaInicio) : null;
			Instant insFechaFin = ObjectUtils.isNotEmpty(fechaFin) ? Instant.parse(fechaFin) : null;
			Page<Credito> resultadoPaginado = creditoService.obtenerCreditos(paginaRequest, filtro, insFechaInicio,
					insFechaFin, estado);
			return ResponseEntity.ok(resultadoPaginado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping(value = "/credito/{id}", produces = "application/json")
	public ResponseEntity<CreditoDTO> getFactura(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(creditoService.obtenerCreditoId(id), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
