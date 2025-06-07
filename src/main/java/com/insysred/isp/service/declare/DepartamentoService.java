package com.insysred.isp.service.declare;

import com.insysred.isp.dto.DepartamentoDTO;
import com.insysred.isp.entities.Departamento;

import java.util.List;

public interface DepartamentoService {

    List<Departamento> lstDepartamento() throws Exception;

    Departamento getDepartamentoById(Long idDepartamento);

    void saveDepartamento(DepartamentoDTO departamento) throws Exception;

    void updateDepartamento(Long id, DepartamentoDTO departamento) throws Exception;
}
