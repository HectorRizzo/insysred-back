package com.insysred.isp.mapper;

import com.insysred.isp.dto.EmpleadosDTO;
import com.insysred.isp.dto.OrdenTrabajoDto;
import com.insysred.isp.entities.Empleados;
import com.insysred.isp.entities.OrdenTrabajo;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrdenTrabajoMapper extends EntityMapper<OrdenTrabajoDto, OrdenTrabajo> {

    OrdenTrabajo toEntity(OrdenTrabajoDto dto);

    OrdenTrabajoDto toDto(OrdenTrabajo entity);

    List<OrdenTrabajo> toEntity(List<OrdenTrabajoDto> dtoList);



}
