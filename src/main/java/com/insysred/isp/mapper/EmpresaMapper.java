package com.insysred.isp.mapper;

import com.insysred.isp.dto.EmpresaDto;
import com.insysred.isp.entities.Empresa;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpresaMapper extends EntityMapper<EmpresaDto, Empresa> {
}
