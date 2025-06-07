package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.PeriodoDTO;
import com.insysred.isp.entities.Periodo;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface PeriodoMapper extends EntityMapper<PeriodoDTO, Periodo> {
}
