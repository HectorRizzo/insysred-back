package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.MkserverrulesDto;
import com.insysred.isp.entities.Mkserverrules;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface MkserverrulesMapper extends EntityMapper<MkserverrulesDto, Mkserverrules> {
}
