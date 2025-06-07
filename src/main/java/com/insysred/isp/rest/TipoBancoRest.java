package com.insysred.isp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insysred.isp.dto.TipoBancoDTO;
import com.insysred.isp.service.declare.TipoBancoService;

@RestController
@RequestMapping("/api")
public class TipoBancoRest {
	@Autowired
	private TipoBancoService tipoBancoService;

	@GetMapping("/tipoBanco")
	public ResponseEntity<List<TipoBancoDTO>> getArchivoConciliacion() {
		try {
			return new ResponseEntity<>(tipoBancoService.listaTiposBanco(), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
