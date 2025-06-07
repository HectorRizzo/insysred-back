package com.insysred.isp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insysred.isp.entities.ParametrosGenerales;
import com.insysred.isp.repository.ParametrosGeneralesRepository;
import com.insysred.isp.service.declare.ParametrosGeneralesService;

@Service
public class ParametrosGeneralesServiceImpl implements ParametrosGeneralesService {

	@Autowired
	private ParametrosGeneralesRepository parametrosGeneralesRepository;

	@Override
	public String obtenerParametroValorString(String id) {
		Optional<ParametrosGenerales> optParametro = parametrosGeneralesRepository.findById(id);
		if (optParametro.isPresent()) {
			return optParametro.get().getValorVarchar();
		}
		return null;
	}

	@Override
	public Long obtenerParametroValorNumber(String id) {
		Optional<ParametrosGenerales> optParametro = parametrosGeneralesRepository.findById(id);
		if (optParametro.isPresent()) {
			return optParametro.get().getValorNumber();
		}
		return (long) 0;
	}
}
