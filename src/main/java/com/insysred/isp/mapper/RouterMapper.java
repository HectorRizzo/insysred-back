package com.insysred.isp.mapper;


import com.insysred.isp.dto.RoutersDto;
import com.insysred.isp.entities.Routers;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouterMapper extends EntityMapper<RoutersDto, Routers> {
}
