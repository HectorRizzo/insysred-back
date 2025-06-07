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
@RequestMapping("/procesos")
public class ProcesosRest {

	@Autowired
	private MkservervlanServiceImpl vlanService;
	
	@Autowired
	private MikroTikService mikroTikService;
	

	@PostMapping(value = "/activar", produces = "application/json")
	public ResponseEntity<ResponseDTO> activar(@RequestBody MkservervlanDto newDto) {
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

	@PostMapping(value = "/suspender_mora", produces = "application/json")
	public ResponseEntity<ResponseDTO> suspender_mora(@RequestBody MkservervlanDto newDto) {
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
	
	@PostMapping(value = "/suspender_corte", produces = "application/json")
	public ResponseEntity<ResponseDTO> suspender_corte(@RequestBody MkservervlanDto newDto) {
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
	
	@PostMapping(value = "/reactivar_mora", produces = "application/json")
	public ResponseEntity<ResponseDTO> reactivar_mora(@RequestBody MkservervlanDto newDto) {
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
	
	@PostMapping(value = "/reactivar_corte", produces = "application/json")
	public ResponseEntity<ResponseDTO> reactivar_corte(@RequestBody MkservervlanDto newDto) {
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
	
	@PostMapping(value = "/retiro", produces = "application/json")
	public ResponseEntity<ResponseDTO> retiro(@RequestBody MkservervlanDto newDto) {
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
	
	@PostMapping(value = "/cambio_plan", produces = "application/json")
	public ResponseEntity<ResponseDTO> cambio_plan(@RequestBody MkservervlanDto newDto) {
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
}
