package com.insysred.isp.service.impl;

import com.insysred.isp.dto.EmpleadosDTO;
import com.insysred.isp.entities.Cargo;
import com.insysred.isp.entities.Departamento;
import com.insysred.isp.entities.Empleados;
import com.insysred.isp.mapper.EmpleadosMapper;
import com.insysred.isp.repository.CargosRepository;
import com.insysred.isp.repository.DepartamentoRepository;
import com.insysred.isp.repository.EmpleadosRepository;
import com.insysred.isp.service.declare.EmpleadosService;
import org.springframework.beans.factory.annotation.Autowired;
import com.insysred.isp.filtros.FiltroEmpleados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EmpleadosServiceImpl implements EmpleadosService {

    @Autowired
    private EmpleadosMapper empleadosMapper;

    @Autowired
    private EmpleadosRepository empleadosRepository;

    @Autowired
    private CargosRepository cargoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    private static Logger logger = Logger.getLogger(EmpleadosServiceImpl.class.getName());

    @Override
    public void saveEmpleados(EmpleadosDTO empleados) throws Exception {
        try{
            Empleados empleado = new Empleados();

            String nombreCompleto = empleados.getPrimerNombre() + " ";
            if(empleados.getSegundoNombre() != null){
                nombreCompleto += empleados.getSegundoNombre() + " ";
            }
            nombreCompleto += empleados.getPrimerApellido();
            if(empleados.getSegundoApellido() != null){
                nombreCompleto += " " +empleados.getSegundoApellido();
            }

            empleado.setPrimerNombre(empleados.getPrimerNombre());
            empleado.setSegundoNombre(empleados.getSegundoNombre());
            empleado.setPrimerApellido(empleados.getPrimerApellido());
            empleado.setSegundoApellido(empleados.getSegundoApellido());
            empleado.setNombreCompleto(nombreCompleto);
            empleado.setTipoIdentificacion(empleados.getTipoIdentificacion());
            empleado.setNumeroIdentificacion(empleados.getIdentificacion());
            empleado.setActivo(true);
            empleado.setSexo(empleados.getSexo());
            empleado.setFechaNacimiento(empleados.getFechaNacimiento());
            empleado.setDireccion(empleados.getDireccion());
            empleado.setTelefonoFijo(empleados.getTelefonoFijo());
            empleado.setTelefonoMovil(empleados.getTelefonoMovil());
            empleado.setCorreo(empleados.getEmail());
            empleado.setFechaIngreso(new Date());
            empleado.setIdCargo(empleados.getIdCargo() != null ? empleados.getIdCargo() : null);
            empleado.setIdDepartamento(empleados.getIdDepartamento() != null ? empleados.getIdDepartamento() : null);
            empleado.setIdJefe(empleados.getIdJefe() != null ? empleados.getIdJefe() : null);
            empleadosRepository.save(empleado);


        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<Empleados> findAll(Pageable pageable, String filter) throws Exception {
        try{
            Sort sort = Sort.by(Sort.Direction.ASC, "id");
            //que busque todos activo
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
            return empleadosRepository.findAll(FiltroEmpleados.contieneTexto(filter,true), pageable);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void updateEmpleados(Long id, EmpleadosDTO empleados) throws Exception {
        try {
            Optional<Empleados> empleado = empleadosRepository.findByIdAndActivo(id);
            if (empleado.isPresent()) {
                Empleados empleadoActualizado = empleado.get();

                String nombreCompleto = empleados.getPrimerNombre() + " ";
                if(empleados.getSegundoNombre() != null){
                    nombreCompleto += empleados.getSegundoNombre() + " ";
                }
                nombreCompleto += empleados.getPrimerApellido();
                if(empleados.getSegundoApellido() != null){
                    nombreCompleto += " " +empleados.getSegundoApellido();
                }
                empleadoActualizado.setPrimerNombre(empleados.getPrimerNombre().equals("") ? empleadoActualizado.getPrimerNombre() : empleados.getPrimerNombre());
                empleadoActualizado.setSegundoNombre(empleados.getSegundoNombre().equals("") ? empleadoActualizado.getSegundoNombre() : empleados.getSegundoNombre());
                empleadoActualizado.setPrimerApellido(empleados.getPrimerApellido().equals("") ? empleadoActualizado.getPrimerApellido() : empleados.getPrimerApellido());
                empleadoActualizado.setSegundoApellido(empleados.getSegundoApellido().equals("") ? empleadoActualizado.getSegundoApellido() : empleados.getSegundoApellido());
                if(!empleadoActualizado.getNombreCompleto().equals(nombreCompleto)){
                    empleadoActualizado.setNombreCompleto(nombreCompleto);
                }
                empleadoActualizado.setTipoIdentificacion(empleados.getTipoIdentificacion().equals("") ? empleadoActualizado.getTipoIdentificacion() : empleados.getTipoIdentificacion());
                empleadoActualizado.setNumeroIdentificacion(empleados.getIdentificacion().equals("") ? empleadoActualizado.getNumeroIdentificacion() : empleados.getIdentificacion());
                empleadoActualizado.setSexo(empleados.getSexo().equals("") ? empleadoActualizado.getSexo() : empleados.getSexo());
                empleadoActualizado.setFechaNacimiento(empleados.getFechaNacimiento() != null ? empleados.getFechaNacimiento() : empleadoActualizado.getFechaNacimiento());
                empleadoActualizado.setDireccion(empleados.getDireccion().equals("") ? empleadoActualizado.getDireccion() : empleados.getDireccion());
                empleadoActualizado.setTelefonoFijo(empleados.getTelefonoFijo().equals("") ? empleadoActualizado.getTelefonoFijo() : empleados.getTelefonoFijo());
                empleadoActualizado.setTelefonoMovil(empleados.getTelefonoMovil().equals("") ? empleadoActualizado.getTelefonoMovil() : empleados.getTelefonoMovil());
                empleadoActualizado.setCorreo(empleados.getEmail().equals("") ? empleadoActualizado.getCorreo() : empleados.getEmail());
                empleadoActualizado.setIdCargo(empleados.getIdCargo() != null ? empleados.getIdCargo() : null);
                empleadoActualizado.setIdDepartamento(empleados.getIdDepartamento() != null ? empleados.getIdDepartamento() : null);
                empleadoActualizado.setIdJefe(empleados.getIdJefe() != null ? empleados.getIdJefe() : null);
                empleadosRepository.save(empleadoActualizado);
            } else {
                throw new Exception("Empleado no encontrado");
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public void inactivarEmpleado(Long id) throws Exception {
        try {
            Optional<Empleados> empleado = empleadosRepository.findByIdAndActivo(id);
            if (empleado.isPresent()) {
                Empleados empleadoInactivado = empleado.get();
                empleadoInactivado.setActivo(false);
                empleadosRepository.save(empleadoInactivado);
            } else {
                throw new Exception("Empleado no encontrado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void asignarDepartamento(Long idEmpleado, Long idDepartamento) throws Exception {
        try {
            Optional<Empleados> empleado = empleadosRepository.findByIdAndActivo(idEmpleado);
            if (empleado.isPresent()) {
                Optional<Departamento> departamento = departamentoRepository.findByIdAndActivo(idDepartamento);
                if (departamento.isEmpty()) {
                    throw new Exception("Departamento no encontrado");
                }
                Empleados empleadoActualizado = empleado.get();
                empleadoActualizado.setIdDepartamento(idDepartamento);
                empleadosRepository.save(empleadoActualizado);
            } else {
                throw new Exception("Empleado no encontrado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void asignarCargo(Long idEmpleado, Long idCargo) throws Exception {
        try {
            Optional<Empleados> empleado = empleadosRepository.findByIdAndActivo(idEmpleado);
            if (empleado.isPresent()) {

                Optional<Cargo> cargo = cargoRepository.findByIdAndActivo(idCargo);
                if (cargo.isEmpty()) {
                    throw new Exception("Cargo no encontrado");
                }
                Empleados empleadoActualizado = empleado.get();
                empleadoActualizado.setIdCargo(idCargo);
                empleadosRepository.save(empleadoActualizado);
            } else {
                throw new Exception("Empleado no encontrado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void asignarJefe(Long idEmpleado, Long idJefe) throws Exception {
        try {
            Optional<Empleados> empleado = empleadosRepository.findByIdAndActivo(idEmpleado);
            if (empleado.isPresent()) {
                Optional<Empleados> jefe = empleadosRepository.findByIdAndActivo(idJefe);
                if (jefe.isEmpty()) {
                    throw new Exception("Jefe no encontrado");
                }
                Empleados empleadoActualizado = empleado.get();
                empleadoActualizado.setIdJefe(idJefe);
                empleadosRepository.save(empleadoActualizado);
            } else {
                throw new Exception("Empleado no encontrado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Empleados> obtenerEmpleadosPorCargo(String strCargo) throws Exception {
        try {
            Optional<Cargo> cargo = cargoRepository.getByNombre(strCargo);
            if(!cargo.isPresent()){
                throw new Exception("Cargo no encontrado");
            }
            Long idCargo = cargo.get().getId();
            return empleadosRepository.obtenerEmpleadosPorCargo(idCargo);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
