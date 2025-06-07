package com.insysred.isp.mapper;

import com.insysred.isp.dto.UsuarioDto;
import com.insysred.isp.entities.UsuarioOld;
import com.insysred.isp.mapper.principal.EntityMapper;
import com.insysred.isp.segurity.models.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface    UsuarioMapper extends EntityMapper<UsuarioDto, Usuario> {
}
