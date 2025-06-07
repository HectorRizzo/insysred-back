package com.insysred.isp.mapper;

import com.insysred.isp.dto.ContratoDto;
import com.insysred.isp.entities.Contrato;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContratoMapper extends EntityMapper<ContratoDto, Contrato> {
}
