package com.insysred.isp.rest;

import com.insysred.isp.dto.ProvinciaDto;
import com.insysred.isp.service.declare.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProvinciaRest {
    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping("/provincias")
    public ResponseEntity<List<ProvinciaDto>> getAllProvincias(){
        try {
            return new ResponseEntity(provinciaService.lstProvincia(), HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/provincias/{id}")
    public ResponseEntity<ProvinciaDto> getProvincias(@PathVariable Long id){
        try {
            ProvinciaDto provinciaDTO = provinciaService.getProvinciaById(id);
            if (provinciaDTO != null){
                return new ResponseEntity(provinciaDTO, HttpStatus.OK);
            }
             return new ResponseEntity<>(provinciaDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/provincias")
    public ResponseEntity<ProvinciaDto> nuevoProducto(@RequestBody ProvinciaDto provinciaDTO) {
        try {
            return ResponseEntity.ok(provinciaService.saveProvincia(provinciaDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
