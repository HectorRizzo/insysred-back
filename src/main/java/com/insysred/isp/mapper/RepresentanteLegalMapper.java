package com.insysred.isp.mapper;

import com.insysred.isp.dto.RepresentanteLegalDto;
import com.insysred.isp.entities.RepresentanteLegal;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepresentanteLegalMapper extends EntityMapper<RepresentanteLegalDto, RepresentanteLegal> {
}
