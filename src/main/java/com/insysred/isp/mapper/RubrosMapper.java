package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.RubrosDTO;
import com.insysred.isp.entities.Rubros;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface RubrosMapper extends EntityMapper<RubrosDTO, Rubros> {
}
