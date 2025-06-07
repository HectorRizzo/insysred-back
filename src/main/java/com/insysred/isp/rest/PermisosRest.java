package com.insysred.isp.rest;

import com.insysred.isp.dto.CambioEstadoPermisoDTO;
import com.insysred.isp.dto.PermisosRolDTO;
import com.insysred.isp.dto.ResponseDTO;
import com.insysred.isp.dto.ResponsePermisosXRolesDTO;
import com.insysred.isp.entities.PermisosXRoles;
import com.insysred.isp.segurity.service.IUsuarioService;
import com.insysred.isp.service.declare.PermisosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class PermisosRest {

    @Autowired
    private PermisosService permisosService;

    @Autowired
    private IUsuarioService usuarioService;

    private static Logger logger = Logger.getLogger(PermisosRest.class.getName());

    @GetMapping("/permisos/modulos")
    public ResponseEntity<ResponseDTO> obtenerModulos() {
        try{
            logger.info("Obteniendo modulos");
            List<Map<String,Object>> permisos = permisosService.obtenerPermisos();
            return new ResponseEntity<>(new ResponseDTO("Permisos obtenidos", 200, permisos), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error al obtener los permisos", 400, e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/permisos")
    public ResponseEntity<ResponseDTO> obtenerPermisos(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size,
                                                       @RequestParam(defaultValue = "") String filtro) {
        try{
            logger.info("Obteniendo permisos");
            Pageable pageable = PageRequest.of(page, size);
            Page<ResponsePermisosXRolesDTO> permisos = permisosService.obtenerTodosPermisos(pageable, filtro);
            return new ResponseEntity<>(new ResponseDTO("Permisos obtenidos", 200, permisos), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error al obtener los permisos", 400, e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/permisos/rol/{idRol}")
    public ResponseEntity<ResponseDTO> obtenerPermisosXRol(@PathVariable Long idRol) {
        try{
            logger.info("Obteniendo permisos");
            List<PermisosXRoles> permisos = permisosService.obtenerListPermisosXRol(idRol);
            return new ResponseEntity<>(new ResponseDTO("Permisos obtenidos", 200, permisos), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error al obtener los permisos", 400, e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/permisos")
    public ResponseEntity<ResponseDTO> guardarPermisos(@RequestBody PermisosRolDTO permisosRolDTO) {
        try{
            logger.info("Guardando permisos");
            permisosService.guardarPermisos(permisosRolDTO);
            return new ResponseEntity<>(new ResponseDTO("Permisos guardados", 200, null), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error al guardar los permisos", 400, e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/permisos/{id}")
    public ResponseEntity<ResponseDTO> actualizarPermisos(@PathVariable Long id, @RequestBody CambioEstadoPermisoDTO cambioEstadoPermisoDTO) {
        try{
            logger.info("Actualizando permisos");
            permisosService.actualizarEstadoPermiso(id, cambioEstadoPermisoDTO.getEstado());
            return new ResponseEntity<>(new ResponseDTO("Permisos actualizados", 200, null), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error al actualizar los permisos", 400, e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/permisos/modulos_usuario")
    public ResponseEntity<ResponseDTO> obtenerModulosUsuario(@RequestParam Long idUsuario, @RequestParam Long idSucursal) {
        try{
            logger.info("Obteniendo modulos");
            List<Map<String,Object>> permisos = usuarioService.obtenerPermisos(idUsuario, idSucursal);
            return new ResponseEntity<>(new ResponseDTO("Permisos obtenidos", 200, permisos), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error al obtener los permisos", 400, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
