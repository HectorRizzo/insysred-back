package com.insysred.isp.service.declare;

import com.insysred.isp.dto.EmpresaDto;

import java.util.List;

public interface EmpresaService {

    List<EmpresaDto> getAllEmpresa();

    EmpresaDto getEmpresaBYId(Long id);

    EmpresaDto guardar(EmpresaDto empresaDTO);

    void eliminar(Long id);

    EmpresaDto actualizarEmp(Long idEmpresa, EmpresaDto empresaDTO);

}
