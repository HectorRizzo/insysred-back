package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.DetalleErrorArchivoBancoDTO;
import com.insysred.isp.entities.DetalleErrorArchivoBanco;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface DetalleErrorArchivoBancoMapper
		extends EntityMapper<DetalleErrorArchivoBancoDTO, DetalleErrorArchivoBanco> {
}
