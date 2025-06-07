package com.insysred.isp.mapper;

import com.insysred.isp.dto.ClienteDto;
import com.insysred.isp.dto.ReferenciaDTO;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Referencia;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReferenciaMapper extends EntityMapper<ReferenciaDTO, Referencia> {
    Cliente toEntity(ClienteDto dto);

    ClienteDto toDto(Cliente entity);
}
