package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.MktServidorDto;
import com.insysred.isp.entities.Mktservidor;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface MktServidorMapper extends EntityMapper<MktServidorDto, Mktservidor> {
}
