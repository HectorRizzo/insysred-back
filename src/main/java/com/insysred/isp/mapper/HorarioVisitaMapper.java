package com.insysred.isp.mapper;

import com.insysred.isp.dto.HorarioVisitaDto;
import com.insysred.isp.entities.HorarioVisita;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HorarioVisitaMapper extends EntityMapper<HorarioVisitaDto, HorarioVisita> {
}
