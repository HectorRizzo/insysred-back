package com.insysred.isp.service.declare;

import com.insysred.isp.dto.PermisosRolDTO;
import com.insysred.isp.dto.ResponsePermisosXRolesDTO;
import com.insysred.isp.entities.PermisosXRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PermisosService {

    List<Map<String, Object>>  obtenerPermisos() throws Exception;
    List<PermisosXRoles> obtenerListPermisosXRol(Long idRol) throws Exception;

    void guardarPermisos(PermisosRolDTO permisosRolDTO) throws Exception;

    Page<ResponsePermisosXRolesDTO> obtenerTodosPermisos(Pageable pageable, String filtro) throws Exception;

    void actualizarEstadoPermiso(Long idPermiso, boolean estado) throws Exception;
}
