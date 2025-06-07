package com.insysred.isp.rest;

import com.insysred.isp.dto.OrdenAtencionNewDto;
import com.insysred.isp.dto.OrdenTrabajoDto;
import com.insysred.isp.dto.OrdenTrabajoNewDto;
import com.insysred.isp.dto.ResponseDTO;
import com.insysred.isp.enums.TipoRespuesta;
import com.insysred.isp.service.declare.OrdenTrabajoService;
import com.insysred.isp.util.RespuestaAPI;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrdenTrabajoRest {
    @Autowired
    private OrdenTrabajoService ordenTrabajoService;

    @GetMapping("/orden_trabajo")
    @Operation(summary = "Ordenes de trabajo para tecnicos", description = "Endpoint para obtener las órdenes de trabajo")
    public ResponseEntity<Page<OrdenTrabajoDto>> getOrdenes(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size,
                                                            @RequestParam(defaultValue = "") String filter){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<OrdenTrabajoDto> ordenTrabajoDTOS = ordenTrabajoService.getAllOrdenTrabajo(pageable, filter);
            return ordenTrabajoDTOS != null ?
                    new ResponseEntity<>(ordenTrabajoDTOS, HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/orden_trabajo/tecnico/{id}")
    public ResponseEntity<Page<OrdenTrabajoDto>> getOrdenesByTecnico(@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String filter){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<OrdenTrabajoDto> ordenTrabajoDTOS = ordenTrabajoService.getAllOrdenTrabajoByTecnico(pageable, id, filter);
            return ordenTrabajoDTOS != null ?
                    new ResponseEntity<>(ordenTrabajoDTOS, HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/orden_trabajo/{id}")
    public ResponseEntity<ResponseDTO> actualizar(@PathVariable Long id, @RequestBody OrdenTrabajoNewDto ordenTrabajoDTO) throws Exception {
        try {
            ordenTrabajoService.actualizar(id, ordenTrabajoDTO);
            return new ResponseEntity<>(new ResponseDTO("Orden de trabajo actualizada",200,null), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Error al actualizar la orden de trabajo", 400, null), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/orden_trabajo")
    public ResponseEntity<OrdenTrabajoDto> guardar(@RequestBody OrdenTrabajoNewDto ordenTrabajoDTO) throws Exception {
        return new ResponseEntity<>(ordenTrabajoService.guardar(ordenTrabajoDTO), HttpStatus.CREATED);
    }

    @PostMapping("/orden_trabajo/atender")
    public ResponseEntity<RespuestaAPI> atenderOrden(@RequestBody OrdenAtencionNewDto ordenAtencionDto) {
        RespuestaAPI respuestaAPI = new RespuestaAPI();
        Boolean atenderOrden = ordenTrabajoService.tratarOrdenAtencion(ordenAtencionDto);
        if (atenderOrden) {
            respuestaAPI.setTipoRespuesta(TipoRespuesta.SUCCESS);
            respuestaAPI.setTextMensaje("Orden de trabajo atendida");
            return new ResponseEntity<>(respuestaAPI, HttpStatus.OK);
        } else {
            respuestaAPI.setTipoRespuesta(TipoRespuesta.ERROR);
            respuestaAPI.setTextMensaje("La orden de trabajo no se pudo atender");
            return new ResponseEntity<>(respuestaAPI, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/orden_trabajo/inactivar/{id}")
    public ResponseEntity<ResponseDTO> inactivarOrden(@PathVariable Long id) {
        try {
            ordenTrabajoService.inactivarOrdenTrabajo(id);
            return new ResponseEntity<>(new ResponseDTO("Orden de trabajo inactivada",200,null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Error al inactivar la orden de trabajo", 400, null), HttpStatus.BAD_REQUEST);
        }
    }

}
