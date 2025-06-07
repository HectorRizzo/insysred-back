package com.insysred.isp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.MkserverrulesDto;
import com.insysred.isp.entities.Mkserverrules;
import com.insysred.isp.mapper.MkserverrulesMapper;
import com.insysred.isp.repository.MkserverrulesRepository;
import com.insysred.isp.service.declare.JasperReportService;
import com.insysred.isp.service.declare.MkserverrulesService;
import com.insysred.isp.util.ConnectMicrotik;

@Service
public class MkserverrulesServiceImpl implements MkserverrulesService {

	@Autowired
	private MkserverrulesRepository mktRuleRepository;

	@Autowired
	private MkserverrulesMapper mktRuleMapper; 

	@Autowired
	private ConnectMicrotik connectMicrotik;
	
	@Autowired
	private JasperReportService jasperReportService;

	@Override
    public MkserverrulesDto save(MkserverrulesDto mktServidorDto) {
		Mkserverrules mktServidor = mktRuleMapper.toEntity(mktServidorDto);
		Mkserverrules savedEntity = mktRuleRepository.save(mktServidor);
        return mktRuleMapper.toDto(savedEntity);
    }

    @Override
    public Optional<MkserverrulesDto> findById(Long id) {
        Optional<Mkserverrules> Mkserverrules = mktRuleRepository.findById(id);
        return Mkserverrules.map(mktRuleMapper::toDto);
    }

    @Override
    public Page<MkserverrulesDto> findAll(Pageable pageable) {
        Page<Mkserverrules> page = mktRuleRepository.findAll(pageable);
        return page.map(mktRuleMapper::toDto);
    }

    @Override
    public void deleteById(Long id) {
    	mktRuleRepository.deleteById(id);
    }

	@Override
	public List<MkserverrulesDto> findAll() {
		 return mktRuleMapper.toDto(mktRuleRepository.findAll());
	}
}
