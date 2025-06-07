package com.insysred.isp.mapper;

import com.insysred.isp.dto.EmpleadosDTO;
import com.insysred.isp.entities.Empleados;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpleadosMapper extends EntityMapper<EmpleadosDTO, Empleados> {
    @Mapping(target = "id", ignore = true)
    Empleados toEntity(EmpleadosDTO dto);

    EmpleadosDTO toDto(Empleados entity);
}
