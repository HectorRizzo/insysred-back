package com.insysred.isp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.MkservernatDto;
import com.insysred.isp.entities.Mkservernat;
import com.insysred.isp.mapper.MkservernatMapper;
import com.insysred.isp.repository.MkservernatRepository;
import com.insysred.isp.service.declare.JasperReportService;
import com.insysred.isp.service.declare.MkservernatService;
import com.insysred.isp.util.ConnectMicrotik;

@Service
public class MkservernatServiceImpl implements MkservernatService {

	@Autowired
	private MkservernatRepository mktNatRepository;

	@Autowired
	private MkservernatMapper mktNatMapper; 

	@Autowired
	private ConnectMicrotik connectMicrotik;
	
	@Autowired
	private JasperReportService jasperReportService;

	@Override
    public MkservernatDto save(MkservernatDto mktServidorDto) {
		Mkservernat mktServidor = mktNatMapper.toEntity(mktServidorDto);
		Mkservernat savedEntity = mktNatRepository.save(mktServidor);
        return mktNatMapper.toDto(savedEntity);
    }

    @Override
    public Optional<MkservernatDto> findById(Long id) {
        Optional<Mkservernat> Mkservernat = mktNatRepository.findById(id);
        return Mkservernat.map(mktNatMapper::toDto);
    }

    @Override
    public Page<MkservernatDto> findAll(Pageable pageable) {
        Page<Mkservernat> page = mktNatRepository.findAll(pageable);
        return page.map(mktNatMapper::toDto);
    }

    @Override
    public void deleteById(Long id) {
    	mktNatRepository.deleteById(id);
    }

	@Override
	public List<MkservernatDto> findAll() {
		 return mktNatMapper.toDto(mktNatRepository.findAll());
	}
}
