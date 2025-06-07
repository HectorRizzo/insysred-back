package com.insysred.isp.rest;

import com.insysred.isp.dto.RepresentanteLegalDto;
import com.insysred.isp.service.declare.RepresentanteLegalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RepresentanteLegalRest {
    @Autowired
    private RepresentanteLegalService representanteLegalService;

    @PostMapping("/representanteLegal")
    public ResponseEntity<RepresentanteLegalDto> nuevoProducto(@RequestBody RepresentanteLegalDto representanteLegalDTO) {
        try {
            return ResponseEntity.ok(representanteLegalService.guardar(representanteLegalDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/representanteLegal/{identificacion}")
    public ResponseEntity<RepresentanteLegalDto> obtenerPorId(@PathVariable String  identificacion) {
        RepresentanteLegalDto representanteLegalDTO = representanteLegalService.getByIdentificacion(identificacion);
        System.out.println("------------------------------");
        System.out.println(representanteLegalDTO);
        return representanteLegalDTO !=null ?
                new ResponseEntity<>(representanteLegalDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
