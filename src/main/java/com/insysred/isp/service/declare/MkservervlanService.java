package com.insysred.isp.service.declare;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.MkservervlanDto;
import com.insysred.isp.dto.PlanesDto;
import com.insysred.isp.entities.Mkservervlan;
@Service
public interface MkservervlanService {
    
	MkservervlanDto save(MkservervlanDto MkservervlanDto);
	
    Optional<MkservervlanDto> findById(Long id);
    
    Page<MkservervlanDto> findAll(Pageable pageable);
    
    List<MkservervlanDto> findAll();
    
    void deleteById(Long id);
    
    Page<Mkservervlan> obtenerVlans(PageRequest paginaRequest, Long idRouter, String filtro, Boolean estado);

    MkservervlanDto actualizarVlan(Long id, MkservervlanDto vlanNewDto);
    
    MkservervlanDto cmbEstadoVlan(CambioEstadoDto cambioEstadoDto);
    
    MkservervlanDto findByName(Long idRouter, String nombre);
    
    List<MkservervlanDto> findByIp(Long idRouter, String ip );
} 
