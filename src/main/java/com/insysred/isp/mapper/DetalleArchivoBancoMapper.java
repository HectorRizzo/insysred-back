package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.DetalleArchivoBancoDTO;
import com.insysred.isp.entities.DetalleArchivoBanco;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface DetalleArchivoBancoMapper
		extends EntityMapper<DetalleArchivoBancoDTO, DetalleArchivoBanco> {
}
