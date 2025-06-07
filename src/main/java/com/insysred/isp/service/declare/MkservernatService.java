package com.insysred.isp.service.declare;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.MkservernatDto;
@Service
public interface MkservernatService {
    
	MkservernatDto save(MkservernatDto MkservernatDto);
    Optional<MkservernatDto> findById(Long id);
    Page<MkservernatDto> findAll(Pageable pageable);
    List<MkservernatDto> findAll();
    void deleteById(Long id);
} 
