package com.insysred.isp.service.impl;

import com.insysred.isp.dto.HorarioVisitaDto;
import com.insysred.isp.mapper.HorarioVisitaMapper;
import com.insysred.isp.repository.HorarioVisitaRepository;
import com.insysred.isp.service.declare.HorarioVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioVisitaServiceImpl implements HorarioVisitaService {


  @Autowired
  private HorarioVisitaRepository horarioVisitaRepository;

  @Autowired
  private HorarioVisitaMapper horarioVisitaMapper;

  @Override
  public List<HorarioVisitaDto> obtenerHorarios() {
    return horarioVisitaMapper.toDto(horarioVisitaRepository.obtenerHorarios());
  }
}
