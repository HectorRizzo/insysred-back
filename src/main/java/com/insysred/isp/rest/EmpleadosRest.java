package com.insysred.isp.rest;

import com.insysred.isp.dto.*;
import com.insysred.isp.entities.Empleados;
import com.insysred.isp.service.declare.EmpleadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmpleadosRest {

    @Autowired
    private EmpleadosService empleadoService;

    @PostMapping("/empleados")
    public ResponseEntity<ResponseDTO> guardar(@RequestBody EmpleadosDTO empleado){
        try {
            empleadoService.saveEmpleados(empleado);
            return ResponseEntity.ok(new ResponseDTO("Empleado guardado", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    @GetMapping("/empleados")
    public ResponseEntity<ResponseDTO> obtenerTodos(@RequestParam int page, @RequestParam int size, @RequestParam String filter) {
        try {
            Page<Empleados> empleados = empleadoService.findAll(PageRequest.of(page, size), filter);
            return ResponseEntity.ok(new ResponseDTO("Empleados encontrados", 200, empleados));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<ResponseDTO> actualizar(@PathVariable Long id, @RequestBody EmpleadosDTO empleado){
        try {
            empleadoService.updateEmpleados(id, empleado);
            return ResponseEntity.ok(new ResponseDTO("Empleado actualizado", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    @PutMapping("/empleados/{id}/inactivar")
    public ResponseEntity<ResponseDTO> inactivar(@PathVariable Long id){
        try {
            empleadoService.inactivarEmpleado(id);
            return ResponseEntity.ok(new ResponseDTO("Empleado inactivado", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }
    //Asignar departamento a empleado
    @PostMapping("/empleados/asignar_departamento")
    public ResponseEntity<ResponseDTO> asignarDepartamento(@RequestBody AsignarDepartamentoDTO asignaDepartamentoDto){
        try {
            empleadoService.asignarDepartamento(asignaDepartamentoDto.getIdEmpleado(), asignaDepartamentoDto.getIdDepartamento());
            return ResponseEntity.ok(new ResponseDTO("Departamento asignado", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    //Asignar cargo a empleado
    @PostMapping("/empleados/asignar_cargo")
    public ResponseEntity<ResponseDTO> asignarCargo(@RequestBody AsignarCargoDTO asignarCargoDTO){
        try {
            empleadoService.asignarCargo(asignarCargoDTO.getIdEmpleado(), asignarCargoDTO.getIdCargo());
            return ResponseEntity.ok(new ResponseDTO("Cargo asignado", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    //Asignar jefe a empleado
    @PostMapping("/empleados/asignar_jefe")
    public ResponseEntity<ResponseDTO> asignarJefe(@RequestBody AsignarJefeDTO asignarJefeDTO){
        try {
            empleadoService.asignarJefe(asignarJefeDTO.getIdEmpleado(), asignarJefeDTO.getIdJefe());
            return ResponseEntity.ok(new ResponseDTO("Jefe asignado", 200, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

    @GetMapping("/empleados/cargo")
    public ResponseEntity<ResponseDTO> obtenerTecnicos(@RequestParam String cargo) {
        try {
            return ResponseEntity.ok(new ResponseDTO("Tecnicos encontrados", 200, empleadoService.obtenerEmpleadosPorCargo(cargo)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), 400, null));
        }
    }

}
