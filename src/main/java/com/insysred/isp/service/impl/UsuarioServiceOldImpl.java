package com.insysred.isp.service.impl;

import com.insysred.isp.dto.UsuarioDto;
import com.insysred.isp.entities.RolOld;
import com.insysred.isp.entities.UsuarioOld;
import com.insysred.isp.mapper.UsuarioMapper;
import com.insysred.isp.repository.RolRepositoryOld;
import com.insysred.isp.repository.UsuarioRepositoryOld;
import com.insysred.isp.service.declare.UsuarioServiceOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceOldImpl{

//    @Autowired
//    private UsuarioRepositoryOld usuarioRepositoryOld;
//
//    @Autowired
//    private UsuarioMapper usuarioMapper;
//
//    @Autowired
//    private RolRepositoryOld rolRepositoryOld;
//
//    @Override
//    public List<UsuarioDto> obtenerTodos() {
//        return usuarioMapper.toDto(usuarioRepositoryOld.findAll());
//    }
//
//    @Override
//    public UsuarioDto obtenerPorId(Long id) {
//        Optional<UsuarioOld> usuarioOptional = usuarioRepositoryOld.findById(id);
//        return usuarioOptional.map(this::convertirAUsuarioDTO).orElse(null);
//    }
//
//    @Override
//    public UsuarioDto guardar(UsuarioDto usuarioDTO) {
//        UsuarioOld usuarioOld = new UsuarioOld();
//        usuarioOld.setNombres(usuarioDTO.getNombres());
//        usuarioOld.setTipoIdentificacion(usuarioDTO.getTipoIdentificacion());
//        usuarioOld.setIdentificacion(usuarioDTO.getIdentificacion());
//        usuarioOld.setApellidos(usuarioDTO.getApellidos());
//        usuarioOld.setNombres(usuarioDTO.getNombres());
//        usuarioOld.setEmail(usuarioDTO.getEmail());
//        usuarioOld.setPassword(usuarioDTO.getPassword());
//        Optional<RolOld> rolOptional = rolRepositoryOld.findById(usuarioDTO.getRol().getId());
//        rolOptional.ifPresent(usuarioOld::setRolOld);
//        usuarioOld.setIsActive(true);
//        UsuarioOld usuarioOldGuardado = usuarioRepositoryOld.save(usuarioOld);
//        return convertirAUsuarioDTO(usuarioOldGuardado);
//    }
//
//    @Override
//    public UsuarioDto actualizar(Long id, UsuarioDto usuarioDTO) {
//        Optional<UsuarioOld> userOptional = usuarioRepositoryOld.findById(id);
//        if (userOptional.isPresent()) {
//            UsuarioOld usuarioOld = userOptional.get();
//            Optional<RolOld> rolOptional = rolRepositoryOld.findById(usuarioDTO.getRol().getId());
//            rolOptional.ifPresent(usuarioOld::setRolOld);
//            usuarioOld.setTipoIdentificacion(usuarioDTO.getTipoIdentificacion());
//            usuarioOld.setIdentificacion(usuarioDTO.getIdentificacion());
//            usuarioOld.setApellidos(usuarioDTO.getApellidos());
//            usuarioOld.setNombres(usuarioDTO.getNombres());
//            usuarioOld.setEmail(usuarioDTO.getEmail());
//            usuarioOld.setPassword(usuarioDTO.getPassword());
//            usuarioOld.setIsActive(true);
//            usuarioRepositoryOld.save(usuarioOld);
//        }
//        return usuarioDTO;
//    }
//
//    @Override
//    public void eliminar(Long id) {
//        usuarioRepositoryOld.deleteById(id);
//    }
//
//    @Override
//    public List<UsuarioDto> obtenerPorScursal(Long codSucursal) {
//       // System.out.println(usuarioRepository.usuarioSucursal(codSucursal));
//        System.out.println(usuarioRepositoryOld.findBySucursalesId(codSucursal));
//        return null;//usuarioMapper.toDto(usuarioRepository.usuarioSucursal(codSucursal));
//    }
//
//    @Override
//    public List<UsuarioDto> obtenerTecn() {
//        return usuarioMapper.toDto(usuarioRepositoryOld.findToTecnicos());
//    }
//
//    private UsuarioDto convertirAUsuarioDTO(UsuarioOld usuarioOld) {
//        return usuarioMapper.toDto(usuarioOld);
//    }
//
//    private UsuarioOld convertirAUsuario(UsuarioDto usuarioDTO) {
//        return usuarioMapper.toEntity(usuarioDTO);
//    }

}
