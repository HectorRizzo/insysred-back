package com.insysred.isp.service.declare;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.MkserverrulesDto;
@Service
public interface MkserverrulesService {
    
	MkserverrulesDto save(MkserverrulesDto MkserverrulesDto);
    Optional<MkserverrulesDto> findById(Long id);
    Page<MkserverrulesDto> findAll(Pageable pageable);
    List<MkserverrulesDto> findAll();
    void deleteById(Long id);
} 
