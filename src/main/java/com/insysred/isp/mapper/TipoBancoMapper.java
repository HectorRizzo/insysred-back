package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.TipoBancoDTO;
import com.insysred.isp.entities.TipoBanco;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface TipoBancoMapper extends EntityMapper<TipoBancoDTO, TipoBanco> {
}
