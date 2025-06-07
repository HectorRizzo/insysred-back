package com.insysred.isp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.MkserveripvlanDto;
import com.insysred.isp.entities.Mkserveripvlan;
import com.insysred.isp.mapper.MkserveripvlanMapper;
import com.insysred.isp.repository.MkserveripvlanRepository;
import com.insysred.isp.service.declare.JasperReportService;
import com.insysred.isp.service.declare.MkserveripvlanService;
import com.insysred.isp.util.ConnectMicrotik;

@Service
public class MkserveripvlanServiceImpl implements MkserveripvlanService {

	@Autowired
	private MkserveripvlanRepository mktIpVlanRepository;

	@Autowired
	private MkserveripvlanMapper mktIpVlanMapper; 

	@Autowired
	private ConnectMicrotik connectMicrotik;
	
	@Autowired
	private JasperReportService jasperReportService;

	@Override
    public MkserveripvlanDto save(MkserveripvlanDto mktServidorDto) {
		Mkserveripvlan mktServidor = mktIpVlanMapper.toEntity(mktServidorDto);
		Mkserveripvlan savedEntity = mktIpVlanRepository.save(mktServidor);
        return mktIpVlanMapper.toDto(savedEntity);
    }

    @Override
    public Optional<MkserveripvlanDto> findById(Long id) {
        Optional<Mkserveripvlan> Mkserveripvlan = mktIpVlanRepository.findById(id);
        return Mkserveripvlan.map(mktIpVlanMapper::toDto);
    }

    @Override
    public Page<MkserveripvlanDto> findAll(Pageable pageable) {
        Page<Mkserveripvlan> page = mktIpVlanRepository.findAll(pageable);
        return page.map(mktIpVlanMapper::toDto);
    }

    @Override
    public void deleteById(Long id) {
    	mktIpVlanRepository.deleteById(id); 
    }

	@Override
	public List<MkserveripvlanDto> findAll() {
		 return mktIpVlanMapper.toDto(mktIpVlanRepository.findAll());
	}

	@Override
	public Page<Mkserveripvlan> obtenerIpVlans(PageRequest paginaRequest, Long idRouter,  Long idVlan, Boolean esActivo) {
		return mktIpVlanRepository.findByIdRouterAndStatus(idRouter, idVlan, esActivo, paginaRequest);
	}

	@Override
	public Page<Mkserveripvlan> obtenerIpVlans(PageRequest paginaRequest, Long idRouter,  Long idVlan, String filtro, Boolean esActivo) {
		return mktIpVlanRepository.findByIdRouterAndStatus(idRouter, idVlan, esActivo, filtro, paginaRequest);
	}
	
	@Override
	public MkserveripvlanDto actualizarIpVlan(Long id, MkserveripvlanDto newDto) {
		Mkserveripvlan item = mktIpVlanRepository.findById(id).get();
		item.setEstado(newDto.getEstado());
		item.setIdContrato(newDto.getIdContrato());
		return mktIpVlanMapper.toDto(mktIpVlanRepository.save(item));
	}

	@Override
	public MkserveripvlanDto cmbEstadoIpVlan(CambioEstadoDto cambioEstadoDto) {
		Mkserveripvlan vlan = mktIpVlanRepository.findById(cambioEstadoDto.getIdComponent()).get();
		vlan.setEsActivo(cambioEstadoDto.getEstatus());
		return mktIpVlanMapper.toDto(mktIpVlanRepository.save(vlan));
	}

	@Override
	public MkserveripvlanDto findByIp(Long idRouter, String ip) {
		Mkserveripvlan vlan = (Mkserveripvlan) mktIpVlanRepository.findByIp(idRouter, ip);
		return mktIpVlanMapper.toDto(vlan);
	}
 
	 
}
