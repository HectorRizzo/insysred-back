package com.insysred.isp.rest;

import com.insysred.isp.dto.*;
import com.insysred.isp.entities.Routers;
import com.insysred.isp.service.declare.RouterService;
import com.insysred.isp.util.GetMicrotik;
import com.insysred.isp.util.ValidateMicrotik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoutersRest {

  @Autowired
  private RouterService routerService;

  @Autowired
  private ValidateMicrotik validateMicrotik;

  @Autowired
  private GetMicrotik getMicrotik;

  @GetMapping("/router")
  public ResponseEntity<List<RoutersDto>> getAllRouter() {
    try {
      return new ResponseEntity<>(routerService.getAllRouter(), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/router")
  public ResponseEntity<RoutersDto> setRouterSucursal(@RequestBody RouterNewDto routersDto) {
    try {
      RoutersDto router = routerService.saveRouter(routersDto);
      if (router.getId() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      } else {
        return ResponseEntity.ok(router);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/router/{id}")
  public ResponseEntity<RoutersDto> getRouter(@PathVariable Long id) {
    try {
      return new ResponseEntity<>(routerService.getRouter(id), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/router/sucursal/{idSucursal}")
  public ResponseEntity<List<RoutersDto>> getRouterBySuc(@PathVariable Long idSucursal) {
    try {
      return new ResponseEntity<>(routerService.getRouterSucursal(idSucursal), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/router/cmb_estado")
  public ResponseEntity<RoutersDto> updateStatusRouter(@RequestBody CambioEstadoDto cambioEstadoDto) {
    try {
      return ResponseEntity.ok(routerService.cambiarEstadoRouter(cambioEstadoDto));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/router/asignar_sucursal")
  public ResponseEntity<RoutersDto> asignarRouterSucursal(@RequestBody AsignaSucursalDto asignaSucursalDto) {
    try {
      return ResponseEntity.ok(routerService.asignarSucursal(asignaSucursalDto));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PutMapping("/router/{id}")
  public ResponseEntity<RoutersDto> updateRouter(@PathVariable Long id, @RequestBody RouterNewDto routersDto) {
    try {
      return new ResponseEntity<>(routerService.updateRouter(id, routersDto), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/router/up_pass")
  public ResponseEntity<RoutersDto> updatePass(@RequestBody ResetPasswordDto resetPasswordDto) {
    try {
      return new ResponseEntity<>(routerService.resetPassRouter(resetPasswordDto), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping(value = "/getRouter",produces = "application/json")
  public ResponseEntity<Routers> getAllRouterTest() {
    try {
      return new ResponseEntity<>(getMicrotik.obtenerMicrotik("45.4.88.130"), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
