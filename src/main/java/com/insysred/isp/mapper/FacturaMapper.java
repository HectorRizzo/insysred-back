package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.FacturaDTO;
import com.insysred.isp.entities.Factura;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface FacturaMapper extends EntityMapper<FacturaDTO, Factura> {
}
