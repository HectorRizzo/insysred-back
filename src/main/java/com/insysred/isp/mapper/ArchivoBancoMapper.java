package com.insysred.isp.mapper;

import org.mapstruct.Mapper;

import com.insysred.isp.dto.ArchivoBancoDTO;
import com.insysred.isp.entities.ArchivoBanco;
import com.insysred.isp.mapper.principal.EntityMapper;

@Mapper(componentModel = "spring")
public interface ArchivoBancoMapper extends EntityMapper<ArchivoBancoDTO, ArchivoBanco> {
}
