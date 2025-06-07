package com.insysred.isp.mapper;

import com.insysred.isp.dto.SucursalDto;
import com.insysred.isp.entities.Sucursales;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SucursalMapper extends EntityMapper<SucursalDto, Sucursales> {
}
