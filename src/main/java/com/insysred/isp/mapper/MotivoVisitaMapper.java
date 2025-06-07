package com.insysred.isp.mapper;

import com.insysred.isp.dto.MotivoVisitaDto;
import com.insysred.isp.entities.MotivoVisita;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MotivoVisitaMapper extends EntityMapper<MotivoVisitaDto, MotivoVisita> {
}
