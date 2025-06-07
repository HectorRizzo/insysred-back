package com.insysred.isp.service.declare;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.insysred.isp.dto.AsociarRubroXContratoDto;
import com.insysred.isp.dto.RubrosDTO;
import com.insysred.isp.entities.RubrosXContrato;

public interface RubrosXContratoService {

	Page<RubrosXContrato> obtenerRubrosXContrato(PageRequest paginaRequest, Long idContrato);

	RubrosDTO guardarRubroXContrato(AsociarRubroXContratoDto rubrosXCobrosDTO);

	RubrosDTO anularRubroXContrato(Long idContrato);

}
