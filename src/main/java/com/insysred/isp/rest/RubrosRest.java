package com.insysred.isp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insysred.isp.dto.AsociarRubroXContratoDto;
import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.RubrosDTO;
import com.insysred.isp.entities.Rubros;
import com.insysred.isp.entities.RubrosXContrato;
import com.insysred.isp.service.declare.RubrosService;
import com.insysred.isp.service.declare.RubrosXContratoService;

@RestController
@RequestMapping("/api")
public class RubrosRest {
	@Autowired
	private RubrosService rubrosService;

	@Autowired
	private RubrosXContratoService rubrosXContratoService;

	@GetMapping("/rubros")
	public ResponseEntity<Page<Rubros>> getAllRubros(@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanoPagina, @RequestParam(defaultValue = "") String filtro) {
		try {
			PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina, Sort.by("id").ascending());
			Page<Rubros> resultadoPaginado = rubrosService.obtenerRubros(paginaRequest, filtro);
			return ResponseEntity.ok(resultadoPaginado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/rubros")
	public ResponseEntity<RubrosDTO> guardarRubro(@RequestBody RubrosDTO rubrosDto) {
		try {
			return new ResponseEntity<>(rubrosService.guardarRubro(rubrosDto), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/rubros/cmb_estado")
	public ResponseEntity<RubrosDTO> cambiarEstado(@RequestBody CambioEstadoDto cambioEstadoDto) {
		try {
			return ResponseEntity.ok(rubrosService.cmbEstadoRubro(cambioEstadoDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/rubrosPorContrato/{idContrato}")
	public ResponseEntity<Page<RubrosXContrato>> getAllRubrosPorContrato(@PathVariable Long idContrato,
			@RequestParam(defaultValue = "0") int pagina, @RequestParam(defaultValue = "10") int tamanoPagina) {
		try {
			PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina, Sort.by("id").ascending());
			Page<RubrosXContrato> resultadoPaginado = rubrosXContratoService.obtenerRubrosXContrato(paginaRequest,
					idContrato);
			return ResponseEntity.ok(resultadoPaginado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/rubrosPorContrato")
	public ResponseEntity<?> postRubrosPorContrato(@RequestBody AsociarRubroXContratoDto asociarRubroXContrato) {
		try {
			return ResponseEntity.ok(rubrosXContratoService.guardarRubroXContrato(asociarRubroXContrato));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("/rubrosPorContrato/{idRubroContrato}")
	public ResponseEntity<?> postRubrosPorContrato(@PathVariable Long idRubroContrato) {
		try {
			return ResponseEntity.ok(rubrosXContratoService.anularRubroXContrato(idRubroContrato));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
