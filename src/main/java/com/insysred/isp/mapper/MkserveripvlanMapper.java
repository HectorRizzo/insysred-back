package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.MkserveripvlanDto;
import com.insysred.isp.entities.Mkserveripvlan;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface MkserveripvlanMapper extends EntityMapper<MkserveripvlanDto, Mkserveripvlan> {
}
