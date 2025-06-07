package com.insysred.isp.mapper;


import com.insysred.isp.dto.InventarioEquipoDTO;
import com.insysred.isp.entities.InventarioEquipos;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventarioEquiposMapper extends EntityMapper<InventarioEquipoDTO, InventarioEquipos> {
//    InventarioEquipoDTO toDto(InventarioEquipos entity);
//
//    InventarioEquipos toEntity(InventarioEquipoDTO dto);
}

