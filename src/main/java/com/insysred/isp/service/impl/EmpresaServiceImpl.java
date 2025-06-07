package com.insysred.isp.service.impl;

import com.insysred.isp.dto.EmpresaDto;
import com.insysred.isp.entities.Empresa;
import com.insysred.isp.mapper.EmpresaMapper;
import com.insysred.isp.repository.EmpresaRepository;
import com.insysred.isp.service.declare.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Override
    public List<EmpresaDto> getAllEmpresa() {
        return empresaMapper.toDto(empresaRepository.findAll());
    }

    @Override
    public EmpresaDto getEmpresaBYId(Long id) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);
        return empresaOptional.map(this::convertirAEmpresaDTO).orElse(null);
    }

    @Override
    public EmpresaDto guardar(EmpresaDto empresaDTO) {
        Empresa empresa = empresaMapper.toEntity(empresaDTO);
        empresa.setIsActive(true);
        return empresaMapper.toDto(empresaRepository.save(empresa));
    }

    @Override
    public void eliminar(Long id) {
        empresaRepository.deleteById(id);
    }

    @Override
    public EmpresaDto actualizarEmp(Long idEmpresa, EmpresaDto empresaDTO) {
        Empresa empresa = empresaRepository.getReferenceById(idEmpresa);
        empresa.setNombre(empresaDTO.getNombre());
        empresa.setDireccion(empresaDTO.getDireccion());
        empresa.setRuc(empresaDTO.getRuc());
        empresa.setNombreComercial(empresaDTO.getNombreComercial());
        return empresaMapper.toDto(empresaRepository.save(empresa));
    }

    private EmpresaDto convertirAEmpresaDTO(Empresa empresa) {
        return empresaMapper.toDto(empresa);
    }
}
