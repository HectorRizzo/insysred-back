package com.insysred.isp.mapper;

import com.insysred.isp.dto.UbicacionDto;
import com.insysred.isp.entities.Ubicacion;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UbicacionMapper extends EntityMapper<UbicacionDto, Ubicacion> {
}
