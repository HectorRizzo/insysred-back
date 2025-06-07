package com.insysred.isp.service.impl;

import com.insysred.isp.dto.RepresentanteLegalDto;
import com.insysred.isp.entities.RepresentanteLegal;
import com.insysred.isp.mapper.RepresentanteLegalMapper;
import com.insysred.isp.repository.RepresentanteLegalRepository;
import com.insysred.isp.service.declare.RepresentanteLegalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepresentanteLegalServiceImpl implements RepresentanteLegalService {

    @Autowired
    private RepresentanteLegalMapper representanteLegalMapper;

    @Autowired
    private RepresentanteLegalRepository representanteLegalRepository;

    @Override
    public RepresentanteLegalDto guardar(RepresentanteLegalDto representanteLegalDTO) {
        RepresentanteLegal representanteLegal = representanteLegalMapper.toEntity(representanteLegalDTO);
        representanteLegal.setIsActive(true);
        return representanteLegalMapper.toDto(representanteLegalRepository.save(representanteLegal));
    }

    @Override
    public RepresentanteLegalDto getByIdentificacion(String identificacion) {
        return representanteLegalMapper.toDto(representanteLegalRepository.getByIdent(identificacion));
    }
}
