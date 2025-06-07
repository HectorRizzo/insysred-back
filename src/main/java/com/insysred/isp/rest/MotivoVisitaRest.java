package com.insysred.isp.rest;

import com.insysred.isp.dto.MotivoVisitaDto;
import com.insysred.isp.service.declare.MotivoVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MotivoVisitaRest {

    @Autowired
    private MotivoVisitaService motivoVisitaService;

    @GetMapping("/motivoVisita")
    public ResponseEntity<List<MotivoVisitaDto>> getAllMotivo(){
        try {
            return new ResponseEntity<>(motivoVisitaService.obtenerMotivosVisita(), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
