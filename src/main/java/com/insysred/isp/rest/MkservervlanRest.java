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
import com.insysred.isp.dto.MkservervlanDto;
import com.insysred.isp.dto.ResponseDTO;
import com.insysred.isp.entities.Mkservervlan;
import com.insysred.isp.service.declare.MikroTikService;
import com.insysred.isp.service.impl.MkservervlanServiceImpl;

@RestController
@RequestMapping("/api")
public class MkservervlanRest {

	@Autowired
	private MkservervlanServiceImpl vlanService;
	
	@Autowired
	private MikroTikService mikroTikService;
	
	@GetMapping(value = "/vlans", produces = "application/json")
	public ResponseEntity<Page<Mkservervlan>> getAllMkservervlan(
			@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanoPagina,
			@RequestParam(defaultValue = "") String filtro,
			@RequestHeader("idRouter") Long idRouter, 
			@RequestHeader("estado") Boolean estado) {
		try {
			vlanService.getMkVlans(idRouter);
			PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina);
			Page<Mkservervlan> resultadoPaginado = vlanService.obtenerVlans(paginaRequest, idRouter, filtro, estado);
			return ResponseEntity.ok(resultadoPaginado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping(value = "/vlans", produces = "application/json")
	public ResponseEntity<ResponseDTO> save(@RequestBody MkservervlanDto newDto) {
		try {
			vlanService.getMkVlans(newDto.getIdRouter());
			ResponseDTO validateResult = vlanService.validateNewVlan(newDto);
			if(validateResult.getStatus().compareTo(200) == 0) {
				MkservervlanDto newItem = vlanService.save(newDto);
				vlanService.buildIpList(newItem);
				mikroTikService.addServerVlan(newItem);
				return new ResponseEntity<>(new ResponseDTO("Registro creado correctamente", 200, newItem), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(validateResult, HttpStatus.OK);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Ocurrió un error al obtener la información requerida", 400, e.getMessage()));
		}
	}

	@PutMapping(value = "/vlans/{id}", produces = "application/json")
	public ResponseEntity<ResponseDTO> actualizarMkservervlan(@PathVariable Long id,
			@RequestBody MkservervlanDto vlanNewDto) {
		try {
			return new ResponseEntity<>(new ResponseDTO("Estados de contrato obtenidos", 200, vlanService.actualizarVlan(id, vlanNewDto)), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Ocurrió un error al intentar ejecutar este requerimiento", 400, e.getMessage()));
		}
	}

	@PostMapping(value = "/vlans/cmb_estado", produces = "application/json")
	public ResponseEntity<MkservervlanDto> cambiarEstado(@RequestBody CambioEstadoDto cambioEstadoDto) {
		try {
			return ResponseEntity.ok(vlanService.cmbEstadoVlan(cambioEstadoDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
}
