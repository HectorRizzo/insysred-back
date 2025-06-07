package com.insysred.isp.rest;

import com.insysred.isp.dto.CantonDto;
import com.insysred.isp.dto.MarcaEquiposDto;
import com.insysred.isp.entities.MarcaEquipos;
import com.insysred.isp.service.declare.MarcaEquiposService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class MarcaEquiposRest {

    private static final Logger logger = Logger.getLogger(MarcaEquiposRest.class.getName());

    @Autowired
    private MarcaEquiposService marcaEquiposService;
    @GetMapping("/marcaEquipos")
    public ResponseEntity<List<MarcaEquiposDto>> getAllMarcaEquipos(){
        try{
            return new ResponseEntity<>(marcaEquiposService.lstMarcaEquipos(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/marcaEquipos/paginacion")
    public ResponseEntity<Page<MarcaEquipos>> getAllMarcaEquiposPaginacion(@RequestParam int pagina, @RequestParam int tamanoPagina,
                                                                           @RequestParam(defaultValue = "") String filtro){
        try{
            return new ResponseEntity<>(marcaEquiposService.lstMarcaEquiposPaginacion(pagina, tamanoPagina, filtro), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/marcaEquipos")
    public ResponseEntity<MarcaEquiposDto> guardar(@RequestBody MarcaEquiposDto marcaEquiposDto){
        logger.info("Guardando marca de equipos");
        logger.info(marcaEquiposDto.toString());
        return new ResponseEntity<>(marcaEquiposService.guardar(marcaEquiposDto), HttpStatus.OK);
    }

    @PutMapping("/marcaEquipos/{id}")
    public ResponseEntity<MarcaEquiposDto> actualizar(@PathVariable Long id,@RequestBody MarcaEquiposDto marcaEquiposDto){
        logger.info("Actualizando marca de equipos");
        logger.info(marcaEquiposDto.toString());
        return new ResponseEntity<>(marcaEquiposService.actualizar(id, marcaEquiposDto), HttpStatus.OK);
    }
}
