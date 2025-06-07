package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.MovClienteXMovBancoDTO;
import com.insysred.isp.entities.MovClienteXMovBanco;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface MovClienteXMovBancoMapper extends EntityMapper<MovClienteXMovBancoDTO, MovClienteXMovBanco> {
}
