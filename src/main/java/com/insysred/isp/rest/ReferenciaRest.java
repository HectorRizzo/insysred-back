package com.insysred.isp.rest;

import com.insysred.isp.dto.ReferenciaDTO;
import com.insysred.isp.service.declare.ReferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReferenciaRest {
    @Autowired
    private ReferenciaService referenciaService;

    @GetMapping("/referencia/{id}")
    public ResponseEntity<ReferenciaDTO> getReferencia(@PathVariable Long id){
        try {
            ReferenciaDTO referenciaDTO = referenciaService.getReferenciaById(id);
            if (referenciaDTO != null){
                return new ResponseEntity(referenciaDTO, HttpStatus.OK);
            }
             return new ResponseEntity<>(referenciaDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //referencia por idCliente
    @GetMapping("/referencia/cliente/{id}")
    public ResponseEntity<ReferenciaDTO> getReferenciaByIdCliente(@PathVariable Long id){
        try {
            ReferenciaDTO referenciaDTO = referenciaService.getReferenciaByIdCliente(id);
            if (referenciaDTO != null){
                return new ResponseEntity(referenciaDTO, HttpStatus.OK);
            }
             return new ResponseEntity<>(referenciaDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
