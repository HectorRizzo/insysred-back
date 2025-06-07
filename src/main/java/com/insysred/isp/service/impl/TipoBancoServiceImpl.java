package com.insysred.isp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.TipoBancoDTO;
import com.insysred.isp.mapper.TipoBancoMapper;
import com.insysred.isp.repository.TipoBancoRepository;
import com.insysred.isp.service.declare.TipoBancoService;

@Service
public class TipoBancoServiceImpl implements TipoBancoService {

	@Autowired
	private TipoBancoRepository tipoBancoRepository;

	@Autowired
	private TipoBancoMapper tipoBancoMapper;

	@Override
	public List<TipoBancoDTO> listaTiposBanco() {
		return tipoBancoMapper.toDto(tipoBancoRepository.findAll());
	}
}
