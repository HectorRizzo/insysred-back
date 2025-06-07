package com.insysred.isp.rest;

import com.insysred.isp.dto.EmpresaDto;
import com.insysred.isp.service.declare.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmpresaRest {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/empresa")
    public List<EmpresaDto> obtenerTodos() {
        return empresaService.getAllEmpresa();
    }

    @GetMapping("/empresa/{id}")
    public ResponseEntity<EmpresaDto> obtenerPorId(@PathVariable Long id) {
        EmpresaDto empresaDTO = empresaService.getEmpresaBYId(id);
        return empresaDTO !=null ?
                new ResponseEntity<>(empresaDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/empresa/logo/{id}")
    public ResponseEntity<byte[]> obtenerLogo(@PathVariable Long id) {
        EmpresaDto empresaDTO = empresaService.getEmpresaBYId(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(empresaDTO.getLogo());
    }

    @PostMapping("/empresa")
    public ResponseEntity<EmpresaDto> guardar(@RequestBody EmpresaDto empresaDTO){
        return new ResponseEntity<>(empresaService.guardar(empresaDTO), HttpStatus.OK);
    }

    @PutMapping("/empresa/{id}")
    public ResponseEntity<EmpresaDto> update(@PathVariable Long id, @RequestBody EmpresaDto empresaDTO){
        return new ResponseEntity<>(empresaService.actualizarEmp(id, empresaDTO), HttpStatus.OK);
    }

}
