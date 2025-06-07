package com.insysred.isp.service.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.AsociarRubroXContratoDto;
import com.insysred.isp.dto.RubrosDTO;
import com.insysred.isp.entities.Contrato;
import com.insysred.isp.entities.Rubros;
import com.insysred.isp.entities.RubrosXContrato;
import com.insysred.isp.enums.EstadoRubroXContrato;
import com.insysred.isp.mapper.RubrosMapper;
import com.insysred.isp.repository.ContratoRepository;
import com.insysred.isp.repository.RubrosRepository;
import com.insysred.isp.repository.RubrosXContratoRepository;
import com.insysred.isp.service.declare.RubrosXContratoService;

@Service
public class RubrosXContratoServiceImpl implements RubrosXContratoService {

	@Autowired
	private RubrosXContratoRepository rubrosXContratoRepository;

	@Autowired
	private RubrosMapper rubrosMapper;

	@Autowired
	private RubrosRepository rubrosRepository;

	@Autowired
	private ContratoRepository contratoRepository;

	@Override
	public Page<RubrosXContrato> obtenerRubrosXContrato(PageRequest paginaRequest, Long idContrato) {
		return rubrosXContratoRepository.getByIdContratoIsActive(idContrato, paginaRequest);
	}

	@Override
	public RubrosDTO guardarRubroXContrato(AsociarRubroXContratoDto asociarRubroXContratoDto) {
		Rubros rubro = rubrosRepository.getReferenceById(asociarRubroXContratoDto.getIdRubro());
		Contrato contrato = contratoRepository.getReferenceById(asociarRubroXContratoDto.getIdContrato());
		RubrosXContrato rubrosXContrato = new RubrosXContrato();
		rubrosXContrato.setContrato(contrato);
		rubrosXContrato.setRubro(rubro);
		rubrosXContrato.setCantidad(asociarRubroXContratoDto.getCantidad());
		rubrosXContrato.setEstado(EstadoRubroXContrato.PENDIENTE);
		rubrosXContrato.setIsActive(true);
		rubrosXContrato.setFechaCreacion(Instant.now());
		rubrosXContratoRepository.save(rubrosXContrato);
		return null;
	}

	@Override
	public RubrosDTO anularRubroXContrato(Long idRubroContrato) {
		RubrosXContrato rubrosXContrato = rubrosXContratoRepository.getReferenceById(idRubroContrato);
		rubrosXContrato.setEstado(EstadoRubroXContrato.ANULADO);
		rubrosXContrato.setIsActive(false);
		rubrosXContrato.setFechaModificacion(Instant.now());
		rubrosXContratoRepository.save(rubrosXContrato);
		return null;
	}
}
