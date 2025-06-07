package com.insysred.isp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.MktServidorDto;
import com.insysred.isp.entities.Mktservidor;
import com.insysred.isp.mapper.MktServidorMapper;
import com.insysred.isp.repository.MktservidoresRepository;
import com.insysred.isp.service.declare.JasperReportService;
import com.insysred.isp.service.declare.MktServidorService;
import com.insysred.isp.util.ConnectMicrotik;

@Service
public class MktServidorServiceImpl implements MktServidorService {

	@Autowired
	private MktservidoresRepository mktServidorRepository;

	@Autowired
	private MktServidorMapper mktServidorMapper; 

	@Autowired
	private ConnectMicrotik connectMicrotik;
	
	@Autowired
	private JasperReportService jasperReportService;

	@Override
    public MktServidorDto save(MktServidorDto mktServidorDto) {
		Mktservidor mktServidor = mktServidorMapper.toEntity(mktServidorDto);
		Mktservidor savedEntity = mktServidorRepository.save(mktServidor);
        return mktServidorMapper.toDto(savedEntity);
    }

    @Override
    public Optional<MktServidorDto> findById(Long id) {
        Optional<Mktservidor> Mktservidor = mktServidorRepository.findById(id);
        return Mktservidor.map(mktServidorMapper::toDto);
    }

    @Override
    public Page<MktServidorDto> findAll(Pageable pageable) {
        Page<Mktservidor> page = mktServidorRepository.findAll(pageable);
        return page.map(mktServidorMapper::toDto);
    }

    @Override
    public void deleteById(Long id) {
    	mktServidorRepository.deleteById(id);
    }

	@Override
	public List<MktServidorDto> findAll() {
		 return mktServidorMapper.toDto(mktServidorRepository.findAll());
	}
}
