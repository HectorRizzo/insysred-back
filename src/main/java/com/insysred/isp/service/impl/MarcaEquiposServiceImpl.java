package com.insysred.isp.service.impl;

import com.insysred.isp.dto.MarcaEquiposDto;
import com.insysred.isp.entities.MarcaEquipos;
import com.insysred.isp.mapper.MarcaEquiposMapper;
import com.insysred.isp.repository.MarcaEquiposRepository;
import com.insysred.isp.service.declare.MarcaEquiposService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MarcaEquiposServiceImpl implements MarcaEquiposService {
    @Autowired
    private MarcaEquiposMapper marcaEquiposMapper;
    @Autowired
    private MarcaEquiposRepository marcaEquiposRepository;
    @Override
    public List<MarcaEquiposDto> lstMarcaEquipos() {
        List<MarcaEquiposDto> marcaEquiposDto = null;
        List<MarcaEquipos> marcaEquipos = marcaEquiposRepository.findAllEquipos();
        return marcaEquiposMapper.toDto(marcaEquipos);

    }

    @Override
    public MarcaEquiposDto guardar(MarcaEquiposDto marcaEquiposDto) {
        marcaEquiposDto.setActivo(true);
        marcaEquiposDto.setFechaCreacion(new Date());
        MarcaEquipos marcaEquipos = marcaEquiposMapper.toEntity(marcaEquiposDto);
        marcaEquiposRepository.save(marcaEquipos);
        return marcaEquiposMapper.toDto(marcaEquipos);
    }

    @Override
    public MarcaEquiposDto actualizar(Long id, MarcaEquiposDto marcaEquiposDto) {
        Optional<MarcaEquipos> marcaEquipos = marcaEquiposRepository.findById(id);
        if(marcaEquipos.isPresent()){
            MarcaEquipos marcaEquipos1 = marcaEquipos.get();
            marcaEquipos1.setNombreMarca(marcaEquiposDto.getNombreMarca());
            marcaEquipos1.setNombreModelo(marcaEquiposDto.getNombreModelo());
            marcaEquipos1.setActivo(marcaEquiposDto.getActivo() != null ? marcaEquiposDto.getActivo() : marcaEquipos1.getActivo());
            marcaEquipos1.setFechaModificacion(new Date());
            marcaEquiposRepository.save(marcaEquipos1);
            return marcaEquiposMapper.toDto(marcaEquipos1);
        }else{
            return null;
        }
    }

    public Page<MarcaEquipos> lstMarcaEquiposPaginacion(int pagina, int cantidad, String filtro){
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(pagina, cantidad, sort);
        return marcaEquiposRepository.findAllEquiposPaginacion(pageRequest, filtro);
    }
}
