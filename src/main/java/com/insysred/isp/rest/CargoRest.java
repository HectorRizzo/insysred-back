package com.insysred.isp.rest;

import com.insysred.isp.dto.CargoDTO;
import com.insysred.isp.dto.ResponseDTO;
import com.insysred.isp.entities.Cargo;
import com.insysred.isp.service.declare.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class CargoRest {

    private static Logger logger = Logger.getLogger(CargoRest.class.getName());
    @Autowired
    private CargoService cargoService;

    @GetMapping("/cargo")
    public ResponseEntity<ResponseDTO> obtenerTodos() {
        try {
            List<Cargo> cargos = cargoService.lstCargo();
            return ResponseEntity.ok(new ResponseDTO("Cargos encontrados", 200, cargos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    @GetMapping("/cargos/departamento/{id}")
    public ResponseEntity<ResponseDTO> obtenerPorDepartamento(@PathVariable Long id) {
        try {
            List<Cargo> cargos = cargoService.obtenerPorDepartamento(id);
            return ResponseEntity.ok(new ResponseDTO("Cargos encontrados", 200, cargos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    @PostMapping("/cargos")
    public ResponseEntity<ResponseDTO> guardar(@RequestBody CargoDTO cargo){
        try {
            cargoService.saveCargo(cargo);
            return ResponseEntity.ok(new ResponseDTO("Cargo guardado", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    @PutMapping("/cargos/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody CargoDTO cargo){
        try {
            cargoService.updateCargo(id, cargo);
            return ResponseEntity.ok(new ResponseDTO("Cargo actualizado", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }
}
