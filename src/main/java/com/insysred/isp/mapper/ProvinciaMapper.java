package com.insysred.isp.mapper;

import com.insysred.isp.dto.ProvinciaDto;
import com.insysred.isp.entities.Provincia;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvinciaMapper extends EntityMapper<ProvinciaDto, Provincia> {
}
