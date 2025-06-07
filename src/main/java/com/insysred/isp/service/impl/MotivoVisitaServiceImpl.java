package com.insysred.isp.service.impl;

import com.insysred.isp.dto.MotivoVisitaDto;
import com.insysred.isp.mapper.MotivoVisitaMapper;
import com.insysred.isp.repository.MotivoVisitaRepository;
import com.insysred.isp.service.declare.MotivoVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotivoVisitaServiceImpl implements MotivoVisitaService {

    @Autowired
    private MotivoVisitaMapper motivoVisitaMapper;

    @Autowired
    private MotivoVisitaRepository motivoVisitaRepository;
    @Override
    public List<MotivoVisitaDto> obtenerMotivosVisita() {
        return motivoVisitaMapper.toDto(motivoVisitaRepository.getALlMotivoVisita());
    }
}
