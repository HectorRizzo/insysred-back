package com.insysred.isp.mapper;

import com.insysred.isp.dto.MarcaEquiposDto;
import com.insysred.isp.entities.MarcaEquipos;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarcaEquiposMapper extends EntityMapper<MarcaEquiposDto, MarcaEquipos> {
    MarcaEquiposDto toDto(MarcaEquipos entity);

    MarcaEquipos toEntity(MarcaEquiposDto dto);
}
