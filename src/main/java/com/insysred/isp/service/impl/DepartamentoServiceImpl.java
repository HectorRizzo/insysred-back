package com.insysred.isp.service.impl;

import com.insysred.isp.dto.DepartamentoDTO;
import com.insysred.isp.entities.Cargo;
import com.insysred.isp.entities.Departamento;
import com.insysred.isp.repository.CargosRepository;
import com.insysred.isp.repository.DepartamentoRepository;
import com.insysred.isp.service.declare.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;


@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    private static Logger logger = Logger.getLogger(DepartamentoServiceImpl.class.getName());
    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private CargosRepository cargosRepository;

    @Override
    public List<Departamento> lstDepartamento() throws Exception {
        try {
            return departamentoRepository.getAllActivos();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Departamento getDepartamentoById(Long idDepartamento) {
        try {
            return departamentoRepository.getOne(idDepartamento);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void saveDepartamento(DepartamentoDTO departamento) throws Exception {
        try{
            logger.info("Guardando departamento");
            logger.info(departamento.toString());
            Departamento departamentoEntity = new Departamento();
            departamentoEntity.setNombre(departamento.getNombre());
            departamentoEntity.setDescripcion(departamento.getDescripcion());
            departamentoEntity.setActivo(departamento.getActivo());
            departamentoRepository.save(departamentoEntity);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void updateDepartamento(Long id, DepartamentoDTO departamento) throws Exception {
        try{
            Departamento departamentoEntity = departamentoRepository.getOne(id);
            departamentoEntity.setNombre(departamento.getNombre().isEmpty() ? departamentoEntity.getNombre() : departamento.getNombre());
            departamentoEntity.setDescripcion(departamento.getDescripcion().isEmpty() ? departamentoEntity.getDescripcion() : departamento.getDescripcion());
            if(departamento.getActivo() != null && !departamento.getActivo()){
                List<Cargo> cargos = cargosRepository.findByDepartamento(id);
                if(cargos.size() > 0){
                    throw new Exception("No se puede inactivar el departamento, tiene cargos asignados");
                }
            }
            departamentoEntity.setActivo(departamento.getActivo() == null ? departamentoEntity.getActivo() : departamento.getActivo());
            departamentoRepository.save(departamentoEntity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
