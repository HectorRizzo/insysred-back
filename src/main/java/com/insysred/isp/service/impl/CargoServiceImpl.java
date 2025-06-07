package com.insysred.isp.service.impl;

import com.insysred.isp.dto.CargoDTO;
import com.insysred.isp.entities.Cargo;
import com.insysred.isp.repository.CargosRepository;
import com.insysred.isp.service.declare.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargosRepository cargosRepository;

    @Override
    public List<Cargo> lstCargo() throws Exception {
        try {
            return cargosRepository.getAllActivos();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Cargo getCargoById(Long idCargo) {
        try {
            return cargosRepository.getOne(idCargo);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void saveCargo(CargoDTO cargo) throws Exception {
        try{
            Cargo cargoEntity = new Cargo();
            cargoEntity.setNombre(cargo.getNombre());
            cargoEntity.setDescripcion(cargo.getDescripcion());
            cargoEntity.setActivo(cargo.getActivo());
            cargoEntity.setIdDepartamento(cargo.getIdDepartamento());
            cargosRepository.save(cargoEntity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void updateCargo(Long id, CargoDTO cargo) throws Exception {
        try{
            Cargo cargoEntity = cargosRepository.getOne(id);
            cargoEntity.setNombre(cargo.getNombre().isEmpty() ? cargoEntity.getNombre() : cargo.getNombre());
            cargoEntity.setDescripcion(cargo.getDescripcion().isEmpty() ? cargoEntity.getDescripcion() : cargo.getDescripcion());
            cargoEntity.setActivo(cargo.getActivo() == null ? cargoEntity.getActivo() : cargo.getActivo());
            cargoEntity.setIdDepartamento(cargo.getIdDepartamento() == null ? cargoEntity.getIdDepartamento() : cargo.getIdDepartamento());
            cargosRepository.save(cargoEntity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Cargo> obtenerPorDepartamento(Long id) throws Exception {
        try {
            return cargosRepository.findByDepartamento(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
