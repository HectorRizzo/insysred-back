package com.insysred.isp.mapper;

import com.insysred.isp.dto.OrdenAtencionDto;
import com.insysred.isp.entities.OrdenAtencion;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdenAtencionMapper extends EntityMapper<OrdenAtencionDto, OrdenAtencion> {
}
