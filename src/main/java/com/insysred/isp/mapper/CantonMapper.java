package com.insysred.isp.mapper;

import com.insysred.isp.dto.CantonDto;
import com.insysred.isp.entities.Canton;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CantonMapper extends EntityMapper<CantonDto, Canton> {
}
