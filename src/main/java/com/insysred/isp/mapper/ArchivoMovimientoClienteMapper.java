package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.ArchivoMovimientoClienteDTO;
import com.insysred.isp.entities.ArchivoMovimientoCliente;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface ArchivoMovimientoClienteMapper
		extends EntityMapper<ArchivoMovimientoClienteDTO, ArchivoMovimientoCliente> {
}
