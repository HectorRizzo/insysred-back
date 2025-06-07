package com.insysred.isp.rest;

import com.insysred.isp.dto.*;
import com.insysred.isp.service.declare.RolServiceOld;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RolRest {

    @Autowired
    private RolServiceOld rolServiceOld;

    /*@GetMapping("/rol")
    @Operation(summary = "Roles de usuario", description = "Endpoint obtener los roles de usuarios")
    public List<RolDTO> obtenerTodos() {
        return rolService.obtenerTodos();
    }
    */

    @GetMapping("/rol")
   // @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Roles de usuario", description = "Endpoint obtener los roles de usuarios")
    private ResponseEntity<Page<RolDto>> GetRoles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<RolDto> rolDTO = rolServiceOld.obtenerTodos(pageable);
            return rolDTO != null ?
                    new ResponseEntity<>(rolDTO, HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/rol/all")
    public ResponseEntity<ResponseDTO> obtenerTodos() {
        try {
            return new ResponseEntity<>(new ResponseDTO("Roles obtenidos", 200, rolServiceOld.obtenerTodos()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error al obtener los roles", 400, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rol/{id}")
    public ResponseEntity<RolDto> obtenerPorId(@PathVariable Long id) {
        RolDto rolDTO = rolServiceOld.obtenerPorId(id);
        return rolDTO != null ?
                new ResponseEntity<>(rolDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/rol")
    public ResponseEntity<RolDto> guardar(@RequestBody RolDto rolDTO) {
        return new ResponseEntity<>(rolServiceOld.guardar(rolDTO), HttpStatus.CREATED);
    }

    @PutMapping("/rol/{id}")
    public ResponseEntity<RolDto> guardar(@PathVariable Long id, @RequestBody RolDto rolDTO) {
        return new ResponseEntity<>(rolServiceOld.actualizarRol(id, rolDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/rol/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rolServiceOld.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/rol/asignar_usuario")
    public ResponseEntity<ResponseDTO> asignarRolUsuario(@RequestBody AsignarRolUsuariosDTO asignarRolUsuariosDTO) {
        try{
            if(asignarRolUsuariosDTO.getRoles().isEmpty()){
                return new ResponseEntity<>(new ResponseDTO("Debe seleccionar al menos un rol", 400, null), HttpStatus.BAD_REQUEST);
            }
            if(asignarRolUsuariosDTO.getIdUsuario() == null){
                return new ResponseEntity<>(new ResponseDTO("Debe seleccionar un usuario", 400, null), HttpStatus.BAD_REQUEST);
            }
            if (asignarRolUsuariosDTO.getIdSucursal() == null){
                return new ResponseEntity<>(new ResponseDTO("Debe seleccionar una sucursal", 400, null), HttpStatus.BAD_REQUEST);
            }
            rolServiceOld.asignarRoles(asignarRolUsuariosDTO.getIdUsuario(), asignarRolUsuariosDTO.getIdSucursal(), asignarRolUsuariosDTO.getRoles());
            return new ResponseEntity<>(new ResponseDTO("Roles asignados correctamente",200, null), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error al asignar los roles", 400, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rol/{id}/roles")
    public ResponseEntity<ResponseDTO> getRolesUsuario(@PathVariable Long id,
                                                       @RequestParam() Long idSucursal) {
        try{
            if(idSucursal == null){
                return new ResponseEntity<>(new ResponseDTO("Debe seleccionar una sucursal", 400, null), HttpStatus.BAD_REQUEST);
            }
            if(id == null){
                return new ResponseEntity<>(new ResponseDTO("Debe seleccionar un usuario", 400, null), HttpStatus.BAD_REQUEST);
            }
            List<RolesXAsignarDTO> listSucursales = rolServiceOld.getRolesAsignados(id, idSucursal);
            return new ResponseEntity<>( new ResponseDTO("Sucursales obtenidas", 200, listSucursales), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error al obtener las sucursales", 400, e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

}
