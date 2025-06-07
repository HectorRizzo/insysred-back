package com.insysred.isp.rest;

import com.insysred.isp.dto.*;
import com.insysred.isp.service.declare.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SucursalRest {

  @Autowired
  private SucursalService sucursalService;

  @GetMapping("/sucursal")
  public ResponseEntity<List<SucursalDto>> getAllSucursalActive() {
    try {
      return new ResponseEntity<>(sucursalService.obtenerActivos(), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/sucursal/usuario/{id}")
  public ResponseEntity<List<SucursalDto>> getSucursalesByUsuarioId(@PathVariable Long id) {
    try {
      return new ResponseEntity<>(sucursalService.obtenerPorUsuario(id), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
  @GetMapping("/sucursal/all")
  public ResponseEntity<List<SucursalDto>> getAllSucursal() {
    try {
      return new ResponseEntity<>(sucursalService.obtenerActivos(), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/sucursal/{id}")
  public ResponseEntity<SucursalDto> obtenerPorId(@PathVariable Long id) {
    SucursalDto sucursalDTO = sucursalService.getSucursalById(id);
    return sucursalDTO != null ?
      new ResponseEntity<>(sucursalDTO, HttpStatus.OK) :
      new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/sucursal")
  public ResponseEntity<SucursalDto> guardar(@RequestBody SucursalDto sucursalDTO) {
    return new ResponseEntity<>(sucursalService.guardar(sucursalDTO), HttpStatus.OK);
  }

  @PutMapping("/sucursal/{id}")
  public ResponseEntity<SucursalDto> actualizar(@PathVariable Long id, @RequestBody SucursalDto sucursalDTO) {
    return new ResponseEntity<>(sucursalService.actualizar(id, sucursalDTO), HttpStatus.OK);
  }

  @GetMapping("/sucursal_cliente/{id_cliente}")
  public ResponseEntity<List<SucursalClienteDto>> getAllSucursalCliente(@PathVariable Long id_cliente) {
    try {
      return new ResponseEntity<>(sucursalService.getSucCliente(id_cliente), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/asignar_sucursal")
  public ResponseEntity<AsignarSucursalClienteDto> asignarSucursal(@RequestBody AsignarSucursalClienteDto asignarSucursalClienteDto) {
    sucursalService.asignarSucursalesCliente(asignarSucursalClienteDto);

    return new ResponseEntity<>(asignarSucursalClienteDto, HttpStatus.OK);  }


  @PostMapping("/sucursal/asignar_usuario")
  public ResponseEntity<ResponseDTO> asignarSucursal(@RequestBody AsignarSucursalUsuarioDTO asignarSucursalUsuarioDto) {
    sucursalService.asignarSucursalesUsuario(asignarSucursalUsuarioDto);

    return new ResponseEntity<>(new ResponseDTO("Usuario asignado exitosamente", 200, null), HttpStatus.OK);
  }

  @GetMapping("/sucursal/{id}/sucursales")
    public ResponseEntity<ResponseDTO> getSucursalesUsuario(@PathVariable Long id) {
      try{
        List<SucursalesXAsignarDTO> listSucursales = sucursalService.getSucursalesAsignadas(id);
        return new ResponseEntity<>( new ResponseDTO("Sucursales obtenidas", 200, listSucursales), HttpStatus.OK);
      }catch (Exception e){
        return new ResponseEntity<>(new ResponseDTO("Ocurrió un error al obtener las sucursales", 400, e.getMessage()), HttpStatus.BAD_REQUEST);

      }
    }
}

