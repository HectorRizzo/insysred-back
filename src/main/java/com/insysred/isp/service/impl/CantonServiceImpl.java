package com.insysred.isp.service.impl;

import com.insysred.isp.dto.CantonDto;
import com.insysred.isp.entities.Canton;
import com.insysred.isp.mapper.CantonMapper;
import com.insysred.isp.repository.CantonRepository;
import com.insysred.isp.repository.ProvinciaRepository;
import com.insysred.isp.service.declare.CantonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CantonServiceImpl implements CantonService {

    @Autowired
    private CantonRepository cantonRepository;

    @Autowired
    private CantonMapper cantonMapper;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Override
    public List<CantonDto> lstCanton() {
        return cantonMapper.toDto(cantonRepository.findAll());
    }

    @Override
    public List<CantonDto> getCantonByProvincia(Long idProvincia) {
        return cantonMapper.toDto(cantonRepository.getCantonByProvinciaId(idProvincia));
    }

    @Override
    public CantonDto getCantonById(Long idCanton) {
        return cantonMapper.toDto(cantonRepository.getReferenceById(idCanton));
    }

    @Override
    public CantonDto saveCanton(CantonDto cantonDTO) {
        Canton canton = cantonMapper.toEntity(cantonDTO);
        canton.setProvincia(provinciaRepository.getReferenceById(cantonDTO.getProvincia().getId()));
        return cantonMapper.toDto(cantonRepository.save(canton));
    }
}
