package com.insysred.isp.service.impl;

import com.insysred.isp.dto.AsignarRolUsuariosDTO;
import com.insysred.isp.dto.RolDto;
import com.insysred.isp.dto.RolesXAsignarDTO;
import com.insysred.isp.entities.RolOld;
import com.insysred.isp.entities.UsuariosRoles;
import com.insysred.isp.mapper.RolMapper;
import com.insysred.isp.repository.RolRepositoryOld;
import com.insysred.isp.repository.UsuarioRolesRepository;
import com.insysred.isp.service.declare.RolServiceOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RolServiceOldImplOld implements RolServiceOld {

    @Autowired
    private RolRepositoryOld rolRepositoryOld;

    @Autowired
    private UsuarioRolesRepository usuarioRolesRepository;

    @Autowired
    private RolMapper rolMapper;
    @Override
    public Page<RolDto> obtenerTodos(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest paginaRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<RolOld> roles = rolRepositoryOld.findAll(paginaRequest);
        return roles.map(rolMapper::toDto);
    }

    @Override
    public List<RolDto> obtenerTodos() {
        return rolMapper.toDto(rolRepositoryOld.findAllActivos());
    }

    @Override
    public RolDto obtenerPorId(Long id) {
        Optional<RolOld> rolOptional = rolRepositoryOld.findById(id);
        return rolOptional.map(this::convertirARolDTO).orElse(null);
    }

    @Override
    public RolDto guardar(RolDto rolDTO) {
        RolOld rolOld = rolMapper.toEntity(rolDTO);
        rolOld.setIsActive(true);
        return rolMapper.toDto(rolRepositoryOld.save(rolOld));
    }

    @Override
    public void eliminar(Long id) {
        rolRepositoryOld.deleteById(id);
    }

    private RolDto convertirARolDTO(RolOld rolOld) {
        return rolMapper.toDto(rolOld);
    }

    @Override
    public RolDto actualizarRol(Long idRol, RolDto rolDTO){
        RolOld rolOld = rolRepositoryOld.getReferenceById(idRol);
        rolOld.setNombre(rolDTO.getNombre());
        rolOld.setDescripcion(rolDTO.getDescripcion());
        rolOld.setIsActive(rolDTO.getIsActive());
        return rolMapper.toDto(rolRepositoryOld.save(rolOld));
    }

    @Override
    public void asignarRoles(Long idUsuario, Long idSucursal, List<RolesXAsignarDTO> roles){
        try{
            List<UsuariosRoles> rolesUsuario = usuarioRolesRepository.findByUsuarioSucursalId(idUsuario, idSucursal);
            for(UsuariosRoles rolUsuario : rolesUsuario) {
                RolesXAsignarDTO rol = roles.stream().filter(r -> r.getId().equals(rolUsuario.getRolId())).findFirst().orElse(null);
                if (rol != null) {
                    rolUsuario.setActivo(rol.getChecked());
                    rolUsuario.setFechaModificacion(new Date());
                    usuarioRolesRepository.save(rolUsuario);
                    roles.remove(rol);
                }
            }
            for (RolesXAsignarDTO rol : roles){
                UsuariosRoles usuarioRol = new UsuariosRoles();
                usuarioRol.setUsuarioId(idUsuario);
                usuarioRol.setRolId(rol.getId());
                usuarioRol.setActivo(rol.getChecked());
                usuarioRol.setFechaIngreso(new Date());
                usuarioRol.setSucursalId(idSucursal);
                usuarioRolesRepository.save(usuarioRol);
            }
        }catch (Exception e){
            throw new RuntimeException("Error al asignar roles al usuario", e);
        }
    }
    @Override
    public List<RolesXAsignarDTO> getRolesAsignados(Long idUsuario, Long idSucursal) throws Exception{
        List<RolesXAsignarDTO> roles = new java.util.ArrayList<>();
        try{
            List<UsuariosRoles> rolesUsuario = usuarioRolesRepository.findByUsuarioSucursalId(idUsuario, idSucursal);
            if(Objects.nonNull(rolesUsuario) && !rolesUsuario.isEmpty()) {
                rolesUsuario.forEach(usuarioRoles -> {
                    RolesXAsignarDTO rol = new RolesXAsignarDTO();
                    rol.setId(usuarioRoles.getRolId());
                    rol.setChecked(usuarioRoles.getActivo());
                    roles.add(rol);
                });
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return roles;
    }

}
