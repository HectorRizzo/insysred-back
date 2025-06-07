package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.DetalleFacturaDTO;
import com.insysred.isp.entities.DetalleFactura;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface DetalleFacturaMapper extends EntityMapper<DetalleFacturaDTO, DetalleFactura> {
}
