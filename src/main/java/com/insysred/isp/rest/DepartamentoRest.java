package com.insysred.isp.rest;

import com.insysred.isp.dto.DepartamentoDTO;
import com.insysred.isp.dto.ResponseDTO;
import com.insysred.isp.entities.Departamento;
import com.insysred.isp.service.declare.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class DepartamentoRest {
    @Autowired
    private DepartamentoService departamentoService;

    private static Logger logger = Logger.getLogger(DepartamentoRest.class.getName());
    @GetMapping("/departamento")
    public ResponseEntity<ResponseDTO> obtenerTodos() {
        try {
            List<Departamento> departamentos = departamentoService.lstDepartamento();
            return ResponseEntity.ok(new ResponseDTO("Departamentos encontrados", 200, departamentos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    @PostMapping("/departamento")
    public ResponseEntity<ResponseDTO> guardar(@RequestBody DepartamentoDTO departamento){
        try {
            logger.info(departamento.toString());
            departamentoService.saveDepartamento(departamento);
            return ResponseEntity.ok(new ResponseDTO("Departamento guardado", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    @PutMapping("/departamento/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody DepartamentoDTO departamento){
        try {
            logger.info(departamento.toString());
            departamentoService.updateDepartamento(id, departamento);
            return ResponseEntity.ok(new ResponseDTO("Departamento actualizado", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

}
