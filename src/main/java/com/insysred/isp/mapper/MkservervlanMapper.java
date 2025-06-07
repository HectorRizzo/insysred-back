package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.MkservervlanDto;
import com.insysred.isp.entities.Mkservervlan;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface MkservervlanMapper extends EntityMapper<MkservervlanDto, Mkservervlan> {
}
