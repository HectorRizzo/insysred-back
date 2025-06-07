package com.insysred.isp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.MkserveripvlanDto;
import com.insysred.isp.entities.Mkserveripvlan;
import com.insysred.isp.enums.EstadoIP;
import com.insysred.isp.service.declare.MkserveripvlanService; 

@RestController
@RequestMapping("/api")
public class MkserveripvlanRest {

	@Autowired
	private MkserveripvlanService ipVlanService;

	@GetMapping(value = "/ipvlans", produces = "application/json")
	public ResponseEntity<Page<Mkserveripvlan>> getAllMkserveripvlan(
			@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanoPagina, 
			@RequestParam(defaultValue = "") String filtro,
			@RequestHeader("idRouter") Long idRouter, 
			@RequestHeader("idVlan") Long idVlan, 
			@RequestHeader("estado") Boolean estado) {
		try {
			PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina);
			Page<Mkserveripvlan> resultadoPaginado = null;
			if (filtro != null && filtro.equals(EstadoIP.LIBRE.name())) {
				resultadoPaginado = ipVlanService.obtenerIpVlans(paginaRequest, idRouter,  idVlan, filtro, estado);
			}else if (filtro != null && filtro.equals(EstadoIP.OCUPADO.name())) {
				resultadoPaginado = ipVlanService.obtenerIpVlans(paginaRequest, idRouter,  idVlan, filtro, estado);
			}else {
				resultadoPaginado = ipVlanService.obtenerIpVlans(paginaRequest, idRouter,  idVlan, estado);
			}
			return ResponseEntity.ok(resultadoPaginado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping(value = "/ipvlans/{id}", produces = "application/json")
	public ResponseEntity<MkserveripvlanDto> actualizarMkserveripvlan(@PathVariable Long id,
			@RequestBody MkserveripvlanDto vlanNewDto) {
		try {
			return ResponseEntity.ok(ipVlanService.actualizarIpVlan(id, vlanNewDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping(value = "/ipvlans/cmb_estado", produces = "application/json")
	public ResponseEntity<MkserveripvlanDto> cambiarEstado(@RequestBody CambioEstadoDto cambioEstadoDto) {
		try {
			return ResponseEntity.ok(ipVlanService.cmbEstadoIpVlan(cambioEstadoDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
