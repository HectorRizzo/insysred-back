package com.insysred.isp.rest;

import com.insysred.isp.dto.HorarioVisitaDto;
import com.insysred.isp.service.declare.HorarioVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HorarioVisistaRest {
    @Autowired
    private HorarioVisitaService horarioVisitaService;

    @GetMapping("/horarioVisita")
    private ResponseEntity<List<HorarioVisitaDto>> getAllHorario(){
        try {
            return new ResponseEntity<>(horarioVisitaService.obtenerHorarios(), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
