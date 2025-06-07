package com.insysred.isp.service.declare;

import java.util.List;

import com.insysred.isp.dto.PeriodoDTO;
import com.insysred.isp.dto.ProcesarPeriodoResultadoDto;

public interface PeriodoService {

	List<PeriodoDTO> obtenerPeriodosAnioActual();
	
	List<PeriodoDTO> obtenerPeriodosAnioActualIndividual();

	ProcesarPeriodoResultadoDto procesarFacturas(Long idPeriodo, Long[] idContratos) throws Exception;

}
