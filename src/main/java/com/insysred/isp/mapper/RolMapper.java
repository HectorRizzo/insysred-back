package com.insysred.isp.mapper;


import com.insysred.isp.dto.RolDto;
import com.insysred.isp.entities.RolOld;
import com.insysred.isp.mapper.principal.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper extends EntityMapper<RolDto, RolOld> {
    //RolDTO toDto(Rol rol);
}
