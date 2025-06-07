package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.FacturaPromesaPagoDTO;
import com.insysred.isp.entities.FacturaPromesaPago;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface FacturaPromesaPagoMapper extends EntityMapper<FacturaPromesaPagoDTO, FacturaPromesaPago> {
}
