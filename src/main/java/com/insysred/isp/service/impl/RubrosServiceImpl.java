package com.insysred.isp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.RubrosDTO;
import com.insysred.isp.entities.Planes;
import com.insysred.isp.entities.Rubros;
import com.insysred.isp.filtros.FiltroRubros;
import com.insysred.isp.mapper.RubrosMapper;
import com.insysred.isp.repository.RubrosRepository;
import com.insysred.isp.service.declare.RubrosService;

@Service
public class RubrosServiceImpl implements RubrosService {

	@Autowired
	private RubrosRepository rubrosRepository;

	@Autowired
	private RubrosMapper rubrosMapper;

	@Override
	public Page<Rubros> obtenerRubros(PageRequest paginaRequest, String filtro) {
		return rubrosRepository.findAll(FiltroRubros.contieneTexto(filtro), paginaRequest);
	}

	@Override
	public RubrosDTO guardarRubro(RubrosDTO rubroDto) {
		Rubros factura = rubrosMapper.toEntity(rubroDto);
		factura.setIsActive(true);
		return rubrosMapper.toDto(rubrosRepository.save(factura));
	}

	@Override
	public RubrosDTO cmbEstadoRubro(CambioEstadoDto cambioEstadoDto) {
		Rubros rubro = rubrosRepository.getReferenceById(cambioEstadoDto.getIdComponent());
		rubro.setIsActive(cambioEstadoDto.getEstatus());
		return rubrosMapper.toDto(rubrosRepository.save(rubro));
	}
}
