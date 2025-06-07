package com.insysred.isp.rest;

import com.insysred.isp.dto.*;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.enums.TipoRespuesta;
import com.insysred.isp.service.declare.CLienteService;
import com.insysred.isp.util.RespuestaAPI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ClienteRest {

    private static Logger logger= Logger.getLogger(ClienteRest.class.getName());
    @Autowired
    private CLienteService cLienteService;


    @PostMapping("/cliente/{id_suc}")
    public ResponseEntity<ResponseDTO> guardar(@Valid @RequestBody ClienteDto clienteDTO, @PathVariable Long id_suc) throws Exception {
        try{
            return new ResponseEntity<>( new ResponseDTO("Cliente guardado", 200, cLienteService.guardar(clienteDTO, id_suc)), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDTO("Error al procesar la solicitud " + e.getMessage(), 500, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cliente")
    public ResponseEntity<Page<ClienteContratoDTO>> getAllCliente(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanoPagina,
            @RequestParam(defaultValue = "") String filtro,
            @RequestParam(defaultValue = "0") long idSucursal
    ) {
        try {
            PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina);
            Page<ClienteContratoDTO> resultadoPaginado = cLienteService.obtenerClientes(paginaRequest, filtro, idSucursal);
            return ResponseEntity.ok(resultadoPaginado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<ClienteDto> actualizar(@PathVariable Long id, @RequestBody ClienteDto clienteDTO) {
        return new ResponseEntity<>(cLienteService.actualizar(id, clienteDTO), HttpStatus.OK);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClienteDto> getCliente(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(cLienteService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/cliente/{id_suc}")
    public ResponseEntity<List<ClienteDto>> getClienteSucursal(@PathVariable Long id_suc) {
        try {
            return new ResponseEntity<>(cLienteService.getBySycursal(id_suc), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/cliente/addSucursal")
    public ResponseEntity<RespuestaAPI> guardar(@RequestBody ClienteSucursalDto clienteSucursalDto) {
        RespuestaAPI respuestaAPI = new RespuestaAPI();
        Boolean agregaSuc = cLienteService.agregarSucursal(clienteSucursalDto);
        if (agregaSuc) {
            respuestaAPI.setTipoRespuesta(TipoRespuesta.SUCCESS);
            respuestaAPI.setTextMensaje("Sucursal agregada con éxito");
            return new ResponseEntity<>(respuestaAPI, HttpStatus.OK);
        } else {
            respuestaAPI.setTipoRespuesta(TipoRespuesta.ERROR);
            respuestaAPI.setTextMensaje("El cliente ya esta agregado a esta sucursal");
            return new ResponseEntity<>(respuestaAPI, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cliente/verificar/{id}")
    public ResponseEntity<ResponseDTO> verificarCliente(@PathVariable String id) {
        try {
            return new ResponseEntity<>(new ResponseDTO("Cliente verificado", 200, cLienteService.verificarCliente(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Error al procesar la solicitud " + e.getMessage(), 500, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
