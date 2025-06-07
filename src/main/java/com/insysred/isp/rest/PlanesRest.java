package com.insysred.isp.rest;

import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.PlanesDto;
import com.insysred.isp.dto.PlanesNewDto;
import com.insysred.isp.dto.ResponseDTO;
import com.insysred.isp.entities.Planes;
import com.insysred.isp.service.declare.PlanesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlanesRest {

  @Autowired
  private PlanesService planesService;

  @GetMapping("/planes")
  public ResponseEntity<Page<Planes>> getAllPlanes(
    @RequestParam(defaultValue = "0") int pagina,
    @RequestParam(defaultValue = "10") int tamanoPagina,
    @RequestParam(defaultValue = "") String filtro,
    @RequestHeader("Idsucursal") Long idSucursal,
    @RequestHeader("estado") String estado) {
    try {
      /* estados de planes
      Todos = t
      activos = a
      inactivos = i
       */
      PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina);
      Page<Planes> resultadoPaginado = planesService.obtenerPlanes(paginaRequest, idSucursal, filtro, estado);
      return ResponseEntity.ok(resultadoPaginado);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/planes")
  public ResponseEntity<ResponseDTO> save(@RequestBody PlanesNewDto planesNewDto) {
    try {
      planesService.guardarPlan(planesNewDto);
      return new ResponseEntity<>(new ResponseDTO("Plan creado exitosamente", 200, null), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(new ResponseDTO("Error al procesar la solicitud " + e.getMessage(), 500, null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/planes/{id}")
  public ResponseEntity<PlanesDto> actualizarPlanes(@PathVariable Long id, @RequestBody PlanesNewDto planesNewDto) {
    try {
      return ResponseEntity.ok(planesService.actualizarPlan(id, planesNewDto));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/planes/cmb_estado")
  public ResponseEntity<PlanesDto> cambiarEstado(@RequestBody CambioEstadoDto cambioEstadoDto) {
    try {
      return ResponseEntity.ok(planesService.cmbEstadoPlan(cambioEstadoDto));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

}
