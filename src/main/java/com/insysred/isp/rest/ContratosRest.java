package com.insysred.isp.rest;

import java.util.List;

import com.insysred.isp.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insysred.isp.dto.CambioEstadoContratoDto;
import com.insysred.isp.dto.ContratoDto;
import com.insysred.isp.dto.ContratoNewDto;
import com.insysred.isp.entities.Contrato;
import com.insysred.isp.service.declare.ContratoServide;

@RestController
@RequestMapping("/api")
public class ContratosRest {

  @Autowired
  private ContratoServide contratoServide;

  @GetMapping("/contratos")
  public ResponseEntity<List<ContratoDto>> getAllContratos() {
    try {
      return new ResponseEntity<>(contratoServide.getAllContratos(), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/contratos")
  public ResponseEntity<ContratoDto> guardar(@RequestBody ContratoNewDto contratoNewDto) {
    return new ResponseEntity<>(contratoServide.guardarContrato(contratoNewDto), HttpStatus.OK);
  }

  @PostMapping("/contratos/cmb_estado")
  public ResponseEntity<ContratoDto> cmbEstado(@RequestBody CambioEstadoContratoDto cambioEstadoContratoDto) throws Exception {
    return new ResponseEntity<>(contratoServide.cmbEstadoContrato(cambioEstadoContratoDto), HttpStatus.OK);
  }

  @GetMapping("/contratos/clientes")
  public ResponseEntity<Page<Contrato>> getAllContratosCliente(
    @RequestParam(defaultValue = "0") int pagina,
    @RequestParam(defaultValue = "10") int tamanoPagina,
    @RequestParam(defaultValue = "") String filtro) {
    try {
      PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina, Sort.by("numContrato").ascending());
      Page<Contrato> resultadoPaginado = contratoServide.getAllContratosClientes(paginaRequest, filtro);
      return ResponseEntity.ok(resultadoPaginado);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/contratos/generarReporte")
  public ResponseEntity<?> generarReporte(@RequestParam() Long idContrato) {
    try {
    	return contratoServide.generarReporteContrato(idContrato);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/contratos/estados")
    public ResponseEntity<ResponseDTO> getEstadosContrato() {
        try {
        return new ResponseEntity<>(new ResponseDTO("Estados de contrato obtenidos", 200, contratoServide.getEstadosContrato()), HttpStatus.OK);
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Ocurrió un error al obtener los estados de contrato", 400, e.getMessage()));
        }
    }

}
