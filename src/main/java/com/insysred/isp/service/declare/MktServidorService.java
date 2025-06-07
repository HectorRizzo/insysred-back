package com.insysred.isp.service.declare;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.MktServidorDto;
@Service
public interface MktServidorService {
    
	MktServidorDto save(MktServidorDto MktServidorDto);
    Optional<MktServidorDto> findById(Long id);
    Page<MktServidorDto> findAll(Pageable pageable);
    List<MktServidorDto> findAll();
    void deleteById(Long id);
} 
