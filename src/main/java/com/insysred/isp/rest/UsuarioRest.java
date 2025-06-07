package com.insysred.isp.rest;

import com.insysred.isp.dto.*;
import com.insysred.isp.segurity.models.Usuario;
import com.insysred.isp.service.declare.UsuarioService;
import jakarta.ejb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UsuarioRest {

    @Autowired
    private UsuarioService usuarioService;

    private static Logger logger = Logger.getLogger(UsuarioRest.class.getName());
    @GetMapping("/usuarios")
    public ResponseEntity<Page<Usuario>> obtenerTodos(@RequestParam(defaultValue = "0") Integer page,
                                                                 @RequestParam(defaultValue = "10") Integer size,
                                                                    @RequestParam(defaultValue = "") String filtro) {
        try {
            logger.info("Obteniendo todos los usuarios");
            PageRequest paginaRequest = PageRequest.of(page, size);
            Page<Usuario> resultadoPaginado = usuarioService.obtenerTodos(paginaRequest, filtro);
            return ResponseEntity.ok(resultadoPaginado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        UsuarioDto usuarioDTO = usuarioService.obtenerPorId(id);
        return usuarioDTO != null ?
                new ResponseEntity<>(usuarioDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<ResponseDTO> guardar(@RequestBody AsignarEmpleadoDTO empleado) throws DuplicateKeyException {
        try{
            Map<String, String> mapResponse = usuarioService.guardar(empleado);
            return new ResponseEntity<>(new ResponseDTO("Usuario creado exitosamente", 200, mapResponse), HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(new ResponseDTO("El usuario ya existe", 400, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(e.getMessage(), 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<ResponseDTO> actualizar(@PathVariable Long id,
                                                  @RequestParam(required = false) Boolean cambiarEstado,
                                                  @RequestBody UsuarioDto usuarioDTO) {
        try{
            UsuarioDto usuarioGuardado = usuarioService.actualizar(id, usuarioDTO, cambiarEstado);
            return new ResponseEntity<>(new ResponseDTO("Usuario actualizado exitosamente", 200, null), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Error al actualizar el usuario", 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/usuarios/{id}/actualizar_contrasena")
    public ResponseEntity<ResponseDTO> actualizarContrasena(@PathVariable Long id, @RequestBody UsuarioDto usuario) {
        try{
            usuarioService.actualizarContrasena(id, usuario.getPassword());
            return new ResponseEntity<>(new ResponseDTO("Contraseña actualizada exitosamente", 200, null), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Error al actualizar la contraseña", 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/usuarios/{id}/resetear_contrasena")
    public ResponseEntity<ResponseDTO> resetearContrasena(@PathVariable Long id) {
        try{
           Map<String, String> mapResponse = usuarioService.resetearContrasena(id);
            return new ResponseEntity<>(new ResponseDTO("Contraseña reseteada exitosamente", 200, mapResponse), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Error al resetear la contraseña", 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/usuarios/sucursal")
//    public ResponseEntity<List<UsuarioDto>> obtenerPorSucursal(@RequestHeader("cod_sucursal") Long codSucursal) {
//        List<UsuarioDto> ususarios = usuarioService.obtenerPorScursal(codSucursal);
//        return ResponseEntity.ok(ususarios);
//    }
//
//    @GetMapping("/usuarios/tecnico")
//    public ResponseEntity<List<UsuarioDto>> obtenerTecnios() {
//        List<UsuarioDto> ususarios = usuarioService.obtenerTecn();
//        return ususarios.size() > 0  ?
//                ResponseEntity.ok(ususarios) :
//                new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
