package com.insysred.isp.service.impl;

import com.insysred.isp.dto.ModulosAsignarDTO;
import com.insysred.isp.dto.PermisosRolDTO;
import com.insysred.isp.dto.ResponsePermisosXRolesDTO;
import com.insysred.isp.entities.Modulos;
import com.insysred.isp.entities.PermisosXRoles;
import com.insysred.isp.filtros.FiltroPermisos;
import com.insysred.isp.repository.PermisosRepository;
import com.insysred.isp.service.declare.PermisosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class PermisosServiceImpl implements PermisosService {

    private static Logger logger = Logger.getLogger(PermisosServiceImpl.class.getName());
    @Autowired
    private PermisosRepository permisosRepository;

    @Override
    public List<Map<String, Object>> obtenerPermisos() throws Exception {
        List<Map<String, Object>> permisos = new ArrayList<>();
        try {
            List<Modulos> modulos = permisosRepository.getModulos();
            Map<Object, Map<String, Object>> permisosModuloPadre = new HashMap<>();

            // Construir el árbol de módulos recursivamente
            for (Modulos modulo : modulos) {
                if (modulo.getPadre() == null) {
                    Map<String, Object> modPadre = construirArbol(modulo, permisosModuloPadre);
                    permisos.add(modPadre);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los permisos" + e.getMessage());
            throw new Exception("Error al obtener los permisos " + e.getMessage());
        }
        return permisos;
    }

    // Función recursiva para construir el árbol de módulos
    private Map<String, Object> construirArbol(Modulos modulo, Map<Object, Map<String, Object>> permisosModuloPadre) {
        Map<String, Object> modPadre = new HashMap<>();
        modPadre.put("id", modulo.getId());
        modPadre.put("route", modulo.getRuta());
        modPadre.put("name", modulo.getNombreModulo());
        modPadre.put("type", "sub");
        modPadre.put("children", new ArrayList<Map<String, Object>>());

        permisosModuloPadre.put(modulo.getId(), modPadre);

        // Construir los hijos de manera recursiva
        List<Modulos> hijos = obtenerHijos(modulo.getId());
        for (Modulos hijo : hijos) {
            Map<String, Object> modHijo = construirArbol(hijo, permisosModuloPadre);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> children = (List<Map<String, Object>>) modPadre.get("children");
            children.add(modHijo);
        }

        return modPadre;
    }

    private List<Modulos> obtenerHijos(Long idPadre) {
        List<Modulos> modulos = permisosRepository.getModulos();
        List<Modulos> hijos = new ArrayList<>();
        for (Modulos modulo : modulos) {
            if (modulo.getPadre() != null && modulo.getPadre().equals(idPadre)) {
                hijos.add(modulo);
            }
        }
        return hijos;
    }

    @Override
    public List<PermisosXRoles> obtenerListPermisosXRol(Long idRol) throws Exception{
        try{
            return permisosRepository.obtenerListPermisosXRol(idRol);
        }catch (Exception e){
            throw new Exception("Error al obtener los permisos del rol " + e.getMessage());
        }
    }

    @Override
    public void guardarPermisos(PermisosRolDTO permisosRolDTO) throws Exception {
        try {
            // Guardar los permisos
            for(ModulosAsignarDTO modulo : permisosRolDTO.getModulos()){
                Optional<PermisosXRoles> permisos = permisosRepository.getPermisoByRolModulo(permisosRolDTO.getIdRol(), modulo.getIdModulo());
                if(permisos.isPresent()) {
                    permisos.get().setActivo(modulo.getChecked());
                    permisos.get().setFechaModificacion(new Date());
                    permisosRepository.save(permisos.get());
                } else {
                    PermisosXRoles permisosXRoles = new PermisosXRoles();
                    permisosXRoles.setIdRol(permisosRolDTO.getIdRol());
                    permisosXRoles.setIdModulo(modulo.getIdModulo());
                    permisosXRoles.setActivo(modulo.getChecked());
                    permisosXRoles.setFechaIngreso(new Date());
                    permisosRepository.save(permisosXRoles);
                }
            }

        } catch (Exception e) {
            throw new Exception("Error al guardar los permisos " + e.getMessage());
        }
    }

    public Page<ResponsePermisosXRolesDTO> obtenerTodosPermisos(Pageable pageable, String filtro) throws Exception {
        try{
            return permisosRepository.obtenerTodosPermisos(pageable, filtro).map(
                    permiso -> new ResponsePermisosXRolesDTO(
                            (Long) permiso[0],
                            (Long) permiso[1],
                            (String) permiso[2],
                            (Long) permiso[3],
                            (String) permiso[4],
                            (Boolean) permiso[5]
                    )
            );
        }catch (Exception e){
            throw new Exception("Error al obtener los permisos " + e.getMessage());
        }
    }

    public void actualizarEstadoPermiso(Long idPermiso, boolean estado) throws Exception{
        try{
            Optional<PermisosXRoles> permisos = permisosRepository.findById(idPermiso);
            if(permisos.isPresent()){
                permisos.get().setActivo(estado);
                permisos.get().setFechaModificacion(new Date());
                permisosRepository.save(permisos.get());
            } else {
                throw new Exception("Permiso no encontrado");
            }
        }catch (Exception e){
            throw new Exception("Error al actualizar el permiso " + e.getMessage());
        }
    }

}
