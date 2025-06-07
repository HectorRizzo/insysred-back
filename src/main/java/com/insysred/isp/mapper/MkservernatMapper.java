package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.MkservernatDto;
import com.insysred.isp.entities.Mkservernat;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface MkservernatMapper extends EntityMapper<MkservernatDto, Mkservernat> {
}
