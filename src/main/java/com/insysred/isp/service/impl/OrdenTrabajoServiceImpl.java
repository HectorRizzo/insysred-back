package com.insysred.isp.service.impl;

import com.insysred.isp.dto.*;
import com.insysred.isp.entities.*;
import com.insysred.isp.enums.EstadoOrden;
import com.insysred.isp.filtros.FiltroOrdenTrabajo;
import com.insysred.isp.mapper.OrdenAtencionMapper;
import com.insysred.isp.mapper.OrdenTrabajoMapper;
import com.insysred.isp.repository.*;
import com.insysred.isp.service.declare.OrdenTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrdenTrabajoServiceImpl implements OrdenTrabajoService {

    @Autowired
    private OrdenTrabajoMapper ordenTrabajoMapper;

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private HorarioVisitaRepository horarioVisitaRepository;

    @Autowired
    private MotivoVisitaRepository motivoVisitaRepository;

    @Autowired
    private UsuarioRepositoryOld usuarioRepositoryOld;

    @Autowired
    private EmpleadosRepository empleadosRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private OrdenAtencionMapper ordenAtencionMapper;

    @Autowired
    private OrdenAtencionRepository ordenAtencionRepository;


    @Override
    public Page<OrdenTrabajoDto> getAllOrdenTrabajo(Pageable pageable, String filtro) {
       try{
           Page<OrdenTrabajo> lstOrdenTrabajo = ordenTrabajoRepository.findAll(FiltroOrdenTrabajo.contieneTexto(filtro),pageable);

           return lstOrdenTrabajo.map(
                   ordenTrabajo -> {
                       OrdenTrabajoDto ordenTrabajoDto = ordenTrabajoMapper.toDto(ordenTrabajo);
                       List<Empleados> tecnicos = new ArrayList<>();
                       List<String> lstTecnicos =ordenTrabajo.getTecnicos() != null ? Arrays.asList(ordenTrabajo.getTecnicos().split(",")): new ArrayList<>();
                       for(String strTecnico :lstTecnicos){
                           Empleados tecnico = empleadosRepository.findById(Long.valueOf(strTecnico)).get();
                           tecnicos.add(tecnico);
                       }
                       ordenTrabajoDto.setLsTecnicos(tecnicos);
                       return ordenTrabajoDto;
                   });
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }


    @Override
    public Page<OrdenTrabajoDto> getAllOrdenTrabajoByTecnico(Pageable pageable, Long idTecnico, String filtro) {
        try{
            Page<OrdenTrabajo> lstOrdenTrabajo = ordenTrabajoRepository.findAll(FiltroOrdenTrabajo.contieneTexto(filtro),pageable);
            Optional<Empleados> empleado = empleadosRepository.findById(idTecnico);
            if (!empleado.isPresent()){
                throw new Exception("No se encontró el empleado");
            }
             List<OrdenTrabajo> ordenesFiltradas = lstOrdenTrabajo.stream().filter(ordenTrabajo -> {
                if(ordenTrabajo.getTecnicos() == null){
                    return false;
                }
                if (!ordenTrabajo.getEsActivo()){
                    return false;
                }
                String[] lstTecnicosOrden = ordenTrabajo.getTecnicos().split(",");
                return Arrays.asList(lstTecnicosOrden).contains(empleado.get().getId().toString());
            }).collect(Collectors.toList());


            Page <OrdenTrabajo> ordenesFiltradasPage = new PageImpl<>(ordenesFiltradas, pageable, ordenesFiltradas.size());

            return ordenesFiltradasPage.map(ordenTrabajo -> {
                            OrdenTrabajoDto ordenTrabajoDto = ordenTrabajoMapper.toDto(ordenTrabajo);
                            List<Empleados> tecnicos = new ArrayList<>();
                            List<String> lstTecnicos =ordenTrabajo.getTecnicos() != null ? Arrays.asList(ordenTrabajo.getTecnicos().split(",")): new ArrayList<>();
                            for(String strTecnico :lstTecnicos){
                                Empleados tecnico = empleadosRepository.findById(Long.valueOf(strTecnico)).get();
                                tecnicos.add(tecnico);
                            }
                            ordenTrabajoDto.setLsTecnicos(tecnicos);
                            return ordenTrabajoDto;

            });

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static Logger logger = Logger.getLogger(ClienteServiceImpl.class.getName());

    @Override
    public OrdenTrabajoDto guardar(OrdenTrabajoNewDto ordenTrabajoDTO) throws Exception {
        OrdenTrabajo ordenTrabajo = new OrdenTrabajo();
        ordenTrabajo.setFechaCrea(Instant.now());
        ordenTrabajo.setFechaVisita(ordenTrabajoDTO.getFechaVisita());
        Optional<HorarioVisita> horaVisita = horarioVisitaRepository.findById(ordenTrabajoDTO.getHoraVisita());
        if (horaVisita.isPresent()){
            ordenTrabajo.setHoraVisita(horaVisita.get());
        }

        logger.info("Orden de trabajo: " + ordenTrabajoDTO.toString());
        Optional<MotivoVisita> motivoVisita = motivoVisitaRepository.findById(ordenTrabajoDTO.getMotivo());
        if (motivoVisita.isPresent()){
            ordenTrabajo.setMotivo(motivoVisita.get());
        }

        String idsTecnicos = "";
        for (int i = 0; i < ordenTrabajoDTO.getTecnicos().size(); i++){
            Optional<Empleados> empleado = empleadosRepository.findByIdAndActivo(ordenTrabajoDTO.getTecnicos().get(i).getId());
            if (empleado.isPresent()){
                idsTecnicos += empleado.get().getId();
                if (i < ordenTrabajoDTO.getTecnicos().size() - 1){
                    idsTecnicos += ",";
                }
            }
        }

        ordenTrabajo.setTecnicos(idsTecnicos);

        Optional<Sucursales> sucursal = sucursalRepository.findById(ordenTrabajoDTO.getSucursal());
        if (sucursal.isPresent()){
            ordenTrabajo.setSucursal(sucursal.get());
        }

        Optional<Cliente> cliente = clienteRepository.findById(ordenTrabajoDTO.getCliente());
        if (cliente.isPresent()){
            ordenTrabajo.setCodigoCliente(cliente.get());
        }

        Optional<Contrato> contrato = contratoRepository.getContratoByNum(ordenTrabajoDTO.getContrato());
        if (contrato.isPresent()){
            ordenTrabajo.setContrato(contrato.get());
        }

        ordenTrabajo.setEsActivo(true);
        ordenTrabajo.setCelularContacto(ordenTrabajoDTO.getTelefonoContacto());
        ordenTrabajo.setPersonaContacto(ordenTrabajoDTO.getPersonaContacto());
        ordenTrabajo.setDireccion(ordenTrabajoDTO.getDireccionReferencia());
        ordenTrabajo.setReferenciaDireccion(ordenTrabajoDTO.getReferenciaDireccion());
        ordenTrabajo.setEstadoOrden(EstadoOrden.GENERADA);
        return ordenTrabajoMapper.toDto(ordenTrabajoRepository.save(ordenTrabajo));
    }

    @Override
    public Boolean tratarOrdenAtencion(OrdenAtencionNewDto ordenAtencionDto){
        Boolean atendido = false;
        try {
            OrdenAtencion ordenAtencion = new OrdenAtencion();
            Optional<UsuarioOld> tecnico = usuarioRepositoryOld.findById(ordenAtencionDto.getTecnico());
            if (tecnico.isPresent()){
                ordenAtencion.setTecnico(tecnico.get());
            }

            Optional<OrdenTrabajo> ordenTrabajo = ordenTrabajoRepository.findById(ordenAtencionDto.getOrdenTrabajo());
            if (ordenTrabajo.isPresent()){
                ordenAtencion.setOrdenTrabajo(ordenTrabajo.get());
                ordenTrabajo.get().setEstadoOrden(ordenAtencionDto.getEstadoOrden());
            }

            ordenAtencion.setEstadoOrden(ordenAtencionDto.getEstadoOrden());
            ordenAtencion.setFechaAtencion(Instant.now());
            ordenAtencion.setDetalle(ordenAtencionDto.getDetalle());
            ordenAtencionRepository.save(ordenAtencion);
            return true;

        } catch (Exception e){
            atendido = false;
            e.printStackTrace();
        }
        return atendido;
    }

    @Override
    public void actualizar(Long id, OrdenTrabajoNewDto ordenTrabajoDTO) throws Exception {
        try {
            Optional<OrdenTrabajo> ordenTrabajo = ordenTrabajoRepository.findById(id);
            if (ordenTrabajo.isPresent()) {
                ordenTrabajo.get().setFechaVisita(ordenTrabajoDTO.getFechaVisita());
                Optional<HorarioVisita> horaVisita = horarioVisitaRepository.findById(ordenTrabajoDTO.getHoraVisita());
                if (horaVisita.isPresent()) {
                    ordenTrabajo.get().setHoraVisita(horaVisita.get());
                }

                Optional<MotivoVisita> motivoVisita = motivoVisitaRepository.findById(ordenTrabajoDTO.getMotivo());
                if (motivoVisita.isPresent()) {
                    ordenTrabajo.get().setMotivo(motivoVisita.get());
                }

                String idsTecnicos = "";
                for (int i = 0; i < ordenTrabajoDTO.getTecnicos().size(); i++) {
                    Optional<Empleados> empleado = empleadosRepository.findByIdAndActivo(ordenTrabajoDTO.getTecnicos().get(i).getId());
                    if (empleado.isPresent()) {
                        idsTecnicos += empleado.get().getId();
                        if (i < ordenTrabajoDTO.getTecnicos().size() - 1) {
                            idsTecnicos += ",";
                        }
                    }
                }

                ordenTrabajo.get().setTecnicos(idsTecnicos);

                Optional<Sucursales> sucursal = sucursalRepository.findById(ordenTrabajoDTO.getSucursal());
                if (sucursal.isPresent()) {
                    ordenTrabajo.get().setSucursal(sucursal.get());
                }

                Optional<Cliente> cliente = clienteRepository.findById(ordenTrabajoDTO.getCliente());
                if (cliente.isPresent()) {
                    ordenTrabajo.get().setCodigoCliente(cliente.get());
                }

                Optional<Contrato> contrato = contratoRepository.getContratoByNum(ordenTrabajoDTO.getContrato());
                if (contrato.isPresent()) {
                    ordenTrabajo.get().setContrato(contrato.get());
                }

                ordenTrabajo.get().setEsActivo(true);
                ordenTrabajo.get().setCelularContacto(ordenTrabajoDTO.getTelefonoContacto());
                ordenTrabajo.get().setPersonaContacto(ordenTrabajoDTO.getPersonaContacto());
                ordenTrabajo.get().setDireccion(ordenTrabajoDTO.getDireccionReferencia());

                ordenTrabajo.get().setReferenciaDireccion(ordenTrabajoDTO.getReferenciaDireccion());
                ordenTrabajo.get().setEstadoOrden(EstadoOrden.GENERADA);
                ordenTrabajoRepository.save(ordenTrabajo.get());
            } else {
                throw new Exception("No se encontró la orden de trabajo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar la orden de trabajo");
        }
    }

    public void inactivarOrdenTrabajo(Long id) throws Exception {
        try {
            Optional<OrdenTrabajo> ordenTrabajo = ordenTrabajoRepository.findById(id);
            if (ordenTrabajo.isPresent()) {
                ordenTrabajo.get().setEsActivo(false);
                ordenTrabajoRepository.save(ordenTrabajo.get());
            } else {
                throw new Exception("No se encontró la orden de trabajo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al inactivar la orden de trabajo");
        }
    }

}
