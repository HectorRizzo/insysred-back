package com.insysred.isp.service.impl;

import com.insysred.isp.dto.ProvinciaDto;
import com.insysred.isp.entities.Provincia;
import com.insysred.isp.mapper.ProvinciaMapper;
import com.insysred.isp.repository.ProvinciaRepository;
import com.insysred.isp.service.declare.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaServiceImpl implements ProvinciaService {

    @Autowired
    ProvinciaRepository provinciaRepository;

    @Autowired
    private ProvinciaMapper provinciaMapper;

    @Override
    public List<ProvinciaDto> lstProvincia() {
        return provinciaMapper.toDto(provinciaRepository.findAll());
    }

    @Override
    public ProvinciaDto getProvinciaById(Long idProvincia) {
        return provinciaMapper.toDto(provinciaRepository.getReferenceById(idProvincia));
    }

    @Override
    public ProvinciaDto saveProvincia(ProvinciaDto provinciaDTO) {
        Provincia provincia = provinciaMapper.toEntity(provinciaDTO);
        return provinciaMapper.toDto(provinciaRepository.save(provincia));
    }

}
