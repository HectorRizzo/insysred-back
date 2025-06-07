package com.insysred.isp.rest;

import com.amazonaws.Response;
import com.insysred.isp.dto.ClienteContratoDTO;
import com.insysred.isp.dto.InventarioEquipoDTO;
import com.insysred.isp.dto.ResponseDTO;
import com.insysred.isp.entities.InventarioEquipos;
import com.insysred.isp.service.declare.InventarioEquiposService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class InventarioEquiposRest {

    private static Logger logger= Logger.getLogger(InventarioEquiposRest.class.getName());
    @Autowired
    private InventarioEquiposService inventarioEquiposService;

    //Obtener Lista de Inventario de Equipos
    @GetMapping("/inventarioEquipos")
    public ResponseEntity<Page<InventarioEquipoDTO>> getAllInventarioEquipos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanoPagina,
            @RequestParam(required = false) Integer factura,
            @RequestParam(required = false) Long idMarcaEquipo,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            @RequestParam(required = false) String ip,
            @RequestParam(required = false) String macAddress,
            @RequestParam(required = false) String modoOperacion,
            @RequestParam(required = false) String activo,
            @RequestParam(required = false) String estado) {
        try {
            PageRequest paginaRequest = PageRequest.of(pagina, tamanoPagina);
            return ResponseEntity.ok(inventarioEquiposService.obtenerInventarioEquipos(paginaRequest,
                    factura, idMarcaEquipo, fechaDesde, fechaHasta, ip, macAddress, modoOperacion, estado, activo));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).build();
        }
    }

    //Guardar Lista de Inventario de Equipos
    @PostMapping("/inventarioEquipos")
    public ResponseEntity<ResponseDTO> postInventarioEquipos(@Valid @RequestBody List<InventarioEquipoDTO> inventarioEquipoDTO) {
        try {
            List<Long> lsId = inventarioEquiposService.guardarInventarioEquipos(inventarioEquipoDTO);
            return ResponseEntity.ok(new ResponseDTO("Inventario de equipos guardado correctamente",200, lsId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(new ResponseDTO("Error al guardar el inventario de equipos ", 400, e.getMessage()));
        }
    }

    //Obtener Inventario de Equipos por ID
    @GetMapping("/inventarioEquipos/{id}")
    public String getInventarioEquiposById(Long id){
        return "Inventario de equipos por id";
    }

    //Obtener los estados validos para configurar el equipo
    @GetMapping("/inventarioEquipos/estados")
    public ResponseEntity<ResponseDTO> getInventarioEquiposEstados(){
        try {
            return ResponseEntity.ok(new ResponseDTO("Estados obtenidos correctamente", 200, inventarioEquiposService.getListaEstados()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(new ResponseDTO("Error al obtener los estados", 400, e.getMessage()));
        }
    }



    @PutMapping("/inventarioEquipos/{id}")
    public ResponseEntity<ResponseDTO> putInventarioEquipos(@PathVariable Long id,@Valid @RequestBody InventarioEquipoDTO inventarioEquipoDTO){
        try{
            InventarioEquipoDTO inventarioEquiposDTO = inventarioEquiposService.actualizar(id, inventarioEquipoDTO);
            return ResponseEntity.ok(new ResponseDTO("Inventario de equipos actualizado correctamente", 200, inventarioEquiposDTO));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(new ResponseDTO("Error al actualizar el inventario de equipos", 400, e.getMessage()));
        }

    }

}
