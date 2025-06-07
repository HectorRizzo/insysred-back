package com.insysred.isp.service.declare;

import com.insysred.isp.dto.CargoDTO;
import com.insysred.isp.entities.Cargo;

import java.util.List;

public interface CargoService {

    List<Cargo> lstCargo() throws Exception;

    Cargo getCargoById(Long idCargo);

    void saveCargo(CargoDTO cargo) throws Exception;

    void updateCargo(Long id, CargoDTO cargo) throws Exception;

    List<Cargo> obtenerPorDepartamento(Long id) throws Exception;
}
