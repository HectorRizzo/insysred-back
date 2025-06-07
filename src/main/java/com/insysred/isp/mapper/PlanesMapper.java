package com.insysred.isp.mapper;

import com.insysred.isp.dto.PlanesDto;
import com.insysred.isp.entities.Planes;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlanesMapper extends EntityMapper<PlanesDto, Planes> {
}
