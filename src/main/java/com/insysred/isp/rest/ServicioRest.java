package com.insysred.isp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ServicioRest {
    @GetMapping("/servicios")
    public ResponseEntity<List<?>> getAllServicio(){
        try{
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/corte")
    public ResponseEntity<?> corteServicio(){
        try {
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/reconexion")
    public ResponseEntity<?> reconexionServicio(){
        try {
            System.out.println("test");
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevoServicio(){
        try {
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
