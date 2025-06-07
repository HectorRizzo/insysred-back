package com.insysred.isp.service.declare;

import com.insysred.isp.dto.ProvinciaDto;

import java.util.List;

public interface ProvinciaService {
    List<ProvinciaDto> lstProvincia();

    ProvinciaDto getProvinciaById(Long idProvincia);

    ProvinciaDto saveProvincia(ProvinciaDto provinciaDTO);
}
