package com.insysred.isp.service.declare;

import com.insysred.isp.dto.RolDto;

import com.insysred.isp.dto.RolesXAsignarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RolServiceOld {

    List<RolDto> obtenerTodos();
    Page<RolDto> obtenerTodos(Pageable pageable);

    RolDto obtenerPorId(Long id);

    RolDto guardar(RolDto rolDTO);

    RolDto actualizarRol(Long id_rol, RolDto rolDTO);

    void eliminar(Long id);

    void asignarRoles(Long idUsuario, Long idSucursal, List<RolesXAsignarDTO> roles);
    List<RolesXAsignarDTO> getRolesAsignados(Long idUsuario, Long idSucursal) throws Exception;
}
