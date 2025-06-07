package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.CreditoDTO;
import com.insysred.isp.entities.Credito;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface CreditoMapper extends EntityMapper<CreditoDTO, Credito> {
}
