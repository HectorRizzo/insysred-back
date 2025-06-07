package com.insysred.isp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insysred.isp.dto.PeriodoDTO;
import com.insysred.isp.dto.ProcesarPeriodoDto;
import com.insysred.isp.dto.ProcesarPeriodoResultadoDto;
import com.insysred.isp.service.declare.PeriodoService;

@RestController
@RequestMapping("/api")
public class PeriodoRest {
	@Autowired
	private PeriodoService periodoService;

	@GetMapping("/periodo")
	public ResponseEntity<List<PeriodoDTO>> getPeriodosMasivo() {
		try {
			return new ResponseEntity<>(periodoService.obtenerPeriodosAnioActual(), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/periodo/individual")
	public ResponseEntity<List<PeriodoDTO>> getPeriodosIndividual() {
		try {
			return new ResponseEntity<>(periodoService.obtenerPeriodosAnioActualIndividual(), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/periodo/procesar")
	public ResponseEntity<?> postProcesarPeriodo(@RequestBody ProcesarPeriodoDto procesarPeriodoDto) {
		try {
			ProcesarPeriodoResultadoDto resultado = periodoService.procesarFacturas(procesarPeriodoDto.getIdPeriodo(),
					procesarPeriodoDto.getIdContratos());
			return ResponseEntity.ok(resultado);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
