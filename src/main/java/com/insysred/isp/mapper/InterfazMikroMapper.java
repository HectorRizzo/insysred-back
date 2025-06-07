package com.insysred.isp.mapper;

import com.insysred.isp.dto.InterfazMikroDto;
import com.insysred.isp.entities.InterfazMikro;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InterfazMikroMapper extends EntityMapper<InterfazMikroDto, InterfazMikro> {
}
