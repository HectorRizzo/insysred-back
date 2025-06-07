package com.insysred.isp.service.declare;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.RubrosDTO;
import com.insysred.isp.entities.Rubros;

public interface RubrosService {

	Page<Rubros> obtenerRubros(PageRequest paginaRequest, String filtro);

	RubrosDTO guardarRubro(RubrosDTO rubroDto);

	RubrosDTO cmbEstadoRubro(CambioEstadoDto cambioEstadoDto);

}
