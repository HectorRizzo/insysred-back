package com.insysred.isp.service.declare;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.MkserveripvlanDto;
import com.insysred.isp.dto.MkservervlanDto;
import com.insysred.isp.entities.Mkserveripvlan;
import com.insysred.isp.entities.Mkservervlan;
@Service
public interface MkserveripvlanService {
    
	MkserveripvlanDto save(MkserveripvlanDto MkserveripvlanDto);
	
    Optional<MkserveripvlanDto> findById(Long id);
    
    Page<MkserveripvlanDto> findAll(Pageable pageable);
    
    List<MkserveripvlanDto> findAll();
    
    void deleteById(Long id);
    
    Page<Mkserveripvlan> obtenerIpVlans(PageRequest paginaRequest, Long idRouter, Long idVlan, String filtro, Boolean estado);
    
    Page<Mkserveripvlan> obtenerIpVlans(PageRequest paginaRequest, Long idRouter, Long idVlan, Boolean estado);

    MkserveripvlanDto actualizarIpVlan(Long id, MkserveripvlanDto vlanNewDto);
    
    MkserveripvlanDto cmbEstadoIpVlan(CambioEstadoDto cambioEstadoDto);
    
    MkserveripvlanDto findByIp(Long idRouter, String ip);
} 
