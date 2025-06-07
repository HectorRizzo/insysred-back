package com.insysred.isp.service.declare;

import com.insysred.isp.dto.EmpleadosDTO;
import com.insysred.isp.entities.Empleados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmpleadosService {

    void saveEmpleados(EmpleadosDTO empleados) throws Exception;

    Page<Empleados> findAll(Pageable pageable, String filter) throws Exception;

    void updateEmpleados(Long id, EmpleadosDTO empleados) throws Exception;

    void inactivarEmpleado(Long id) throws Exception;

    void asignarDepartamento(Long idEmpleado, Long idDepartamento) throws Exception;

    void asignarCargo(Long idEmpleado, Long idCargo) throws Exception;

    void asignarJefe(Long idEmpleado, Long idJefe) throws Exception;

    List<Empleados> obtenerEmpleadosPorCargo(String strCargo) throws Exception;
}
