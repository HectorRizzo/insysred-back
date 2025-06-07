package com.insysred.isp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CobranzaRest {
    @GetMapping("/cobranza")
    public ResponseEntity<List<?>> getAllCobranza(){
        try{
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
