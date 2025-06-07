package com.insysred.isp.service.impl;

import com.insysred.isp.dto.InventarioEquipoDTO;
import com.insysred.isp.entities.EstadoEquipo;
import com.insysred.isp.entities.InventarioEquipos;
import com.insysred.isp.entities.MarcaEquipos;
import com.insysred.isp.filtros.FiltroInventarioEquipos;
import com.insysred.isp.mapper.InventarioEquiposMapper;
import com.insysred.isp.repository.EstadoEquipoRepository;
import com.insysred.isp.repository.InventarioEquiposRepository;
import com.insysred.isp.repository.MarcaEquiposRepository;
import com.insysred.isp.service.declare.InventarioEquiposService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class InventarioEquiposServiceImpl implements InventarioEquiposService {

    @Autowired
    private InventarioEquiposRepository inventarioEquiposRepository;

    @Autowired
    private EstadoEquipoRepository estadoEquipoRepository;

    @Autowired
    private MarcaEquiposRepository marcaEquiposRepository;
    private static Logger logger= Logger.getLogger(InventarioEquiposServiceImpl.class.getName());
    @Autowired
    private InventarioEquiposMapper inventarioEquiposMapper;

    @Override
    public Page<InventarioEquipoDTO> obtenerInventarioEquipos(PageRequest paginaRequest,
                                                              Integer factura,
                                                              Long idMarcaEquipo,
                                                                String fechaDesde,
                                                                String fechaHasta,
                                                                String ip,
                                                                String macAddress,
                                                                String modoOperacion,
                                                                String estado,
                                                                String activo) throws Exception {
        try{

            Instant fechaDesdeInstant =  fechaDesde != null ? Instant.parse(fechaDesde) : null;
            MarcaEquipos marcaEquipo =null;
            Instant fechaHastaInstant =  fechaDesde != null ? Instant.parse(fechaHasta) : null;
            if(fechaDesdeInstant != null && fechaHastaInstant != null && fechaDesdeInstant.isAfter(fechaHastaInstant)){
                throw new Exception("La fecha desde no puede ser mayor a la fecha hasta");
            }
            if(idMarcaEquipo != null && idMarcaEquipo <= 0){
                throw new Exception("El id de la marca de equipo no puede ser menor o igual a 0");
            }

            if(idMarcaEquipo != null){
                Optional<MarcaEquipos> marcaEquipos = marcaEquiposRepository.findById(idMarcaEquipo);
                if(!marcaEquipos.isPresent()){
                    throw new Exception("La marca de equipo no existe");
                }else{
                    marcaEquipo = marcaEquipos.get();
                }
            }

            Sort sort = Sort.by(Sort.Direction.ASC, "id");

            Page<InventarioEquipos> inventarioEquipos = inventarioEquiposRepository.findAll(FiltroInventarioEquipos.contieneDatos(
                    factura, fechaDesdeInstant,fechaHastaInstant, ip, macAddress, marcaEquipo, modoOperacion, estado, activo),
                    paginaRequest.withSort(sort));

            logger.info("Inventario de equipos obtenido");
            logger.info(inventarioEquipos.toString());
            return inventarioEquipos.map(inventarioEquipo -> new InventarioEquipoDTO(inventarioEquipo));
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error al obtener los equipos");

        }
    }

    @Override
    public InventarioEquipoDTO guardar(Integer factura,
                                       Long idMarcaEquipo,
                                       String fechaCompra,
                                       String ip,
                                       String macAddress,
                                       String modoOperacion,
                                       String estado) throws Exception {
        Instant fechaCompraInstant = fechaCompra!= null ?
                Instant.parse(fechaCompra) : null;
        MarcaEquipos marcaEquipo =null;

        if(idMarcaEquipo != null){
            Optional<MarcaEquipos> marcaEquipos = marcaEquiposRepository.findById(idMarcaEquipo);
            if(!marcaEquipos.isPresent()){
                throw new Exception("La marca de equipo no existe");
            }else{
                marcaEquipo = marcaEquipos.get();
            }
        }
        try{
            Optional<EstadoEquipo> estadoEquipo = estadoEquipoRepository.findById(Long.parseLong(estado));
            if(!estadoEquipo.isPresent()){
                throw new Exception("El estado del equipo no existe");
            }
            InventarioEquipos inventarioEquipos = new InventarioEquipos();
            inventarioEquipos.setFacturaCompra(factura.toString());
            inventarioEquipos.setFechaCompra(fechaCompraInstant);
            inventarioEquipos.setIp(ip);
            inventarioEquipos.setMarcaEquipos(marcaEquipo);
            inventarioEquipos.setIdEstado(estadoEquipo.get().getId());
            inventarioEquipos.setMacAddress(macAddress);
            inventarioEquipos.setModoOperacion(modoOperacion);

            inventarioEquiposRepository.save(inventarioEquipos);

            return inventarioEquiposMapper.toDto(inventarioEquipos);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error al guardar el equipo" + e.getMessage());
        }

    }

    @Override
    public InventarioEquipoDTO actualizar(Long id, InventarioEquipoDTO inventarioEquipoDTO) {
        try{
            Optional<InventarioEquipos> inventarioEquipos = inventarioEquiposRepository.findById(id);
            if(inventarioEquipos.isPresent()){
                InventarioEquipos inventarioEquipos1 = inventarioEquipos.get();
                inventarioEquipos1.setFacturaCompra(inventarioEquipoDTO.getFacturaCompra() != null ? inventarioEquipoDTO.getFacturaCompra() : inventarioEquipos1.getFacturaCompra());
                inventarioEquipos1.setFechaCompra(inventarioEquipoDTO.getFechaCompra() != null ? inventarioEquipoDTO.getFechaCompra() : inventarioEquipos1.getFechaCompra());
                inventarioEquipos1.setIp(inventarioEquipoDTO.getIp() != null ? inventarioEquipoDTO.getIp() : inventarioEquipos1.getIp());
                inventarioEquipos1.setMacAddress(inventarioEquipoDTO.getMacAddress() != null ? inventarioEquipoDTO.getMacAddress() : inventarioEquipos1.getMacAddress());
                inventarioEquipos1.setModoOperacion(inventarioEquipoDTO.getModoOperacion() != null ? inventarioEquipoDTO.getModoOperacion() : inventarioEquipos1.getModoOperacion());
                if(inventarioEquipoDTO.getIdMarcaEquipo() != null){
                    Optional<MarcaEquipos> marcaEquipos = marcaEquiposRepository.findById(inventarioEquipoDTO.getIdMarcaEquipo());
                    if(marcaEquipos.isPresent()){
                        inventarioEquipos1.setMarcaEquipos(marcaEquipos.get());
                    }
                }else{
                    inventarioEquipos1.setMarcaEquipos(inventarioEquipos1.getMarcaEquipos());
                }
                inventarioEquipos1.setActivo(inventarioEquipoDTO.getActivo() != null ? inventarioEquipoDTO.getActivo() : inventarioEquipos1.getActivo());
                inventarioEquipos1.setIdEstado(inventarioEquipoDTO.getIdEstado() != null ? inventarioEquipoDTO.getIdEstado() : inventarioEquipos1.getIdEstado());
                inventarioEquiposRepository.save(inventarioEquipos1);
                logger.info(String.valueOf(inventarioEquipos1.getId()));
                return inventarioEquiposMapper.toDto(inventarioEquipos1);
            }else{
                throw new Exception("El Equipo no existe");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InventarioEquipoDTO getById(Long idInventarioEquipo) {
        return null;
    }

    @Override
    public List<Long> guardarInventarioEquipos(List<InventarioEquipoDTO> inventarioEquipoDTO) throws Exception {
        try {

            List<InventarioEquipos> inventarioEquipos = inventarioEquipoDTO.stream().map(inventarioEquipo -> {
                InventarioEquipos inventarioEquipos1 = new InventarioEquipos();
                Optional<MarcaEquipos> marcaEquipos;
                try {
                    logger.info("Ingresando a guardar inventario de equipos " + inventarioEquipo.toString());
                    marcaEquipos = marcaEquiposRepository.findById(inventarioEquipo.getIdMarcaEquipo());
                    if (!marcaEquipos.isPresent()) {
                        throw new Exception("La marca de equipo no existe");
                    }
                }catch (Exception e){
                    throw new RuntimeException(e.getMessage());
                }
                inventarioEquipos1.setFacturaCompra(inventarioEquipo.getFacturaCompra());
                inventarioEquipos1.setFechaCompra(inventarioEquipo.getFechaCompra());
                inventarioEquipos1.setIp(inventarioEquipo.getIp());
                inventarioEquipos1.setMacAddress(inventarioEquipo.getMacAddress());
                inventarioEquipos1.setIdMarca(inventarioEquipo.getIdMarcaEquipo());
//                inventarioEquipos1.setMarcaEquipos(marcaEquipos.get());
                inventarioEquipos1.setModoOperacion(inventarioEquipo.getModoOperacion());
                if(inventarioEquipo.getEstado() != null){
                    Optional<EstadoEquipo> estadoEquipo = estadoEquipoRepository.findById(Long.parseLong(inventarioEquipo.getEstado()));
                    if(!estadoEquipo.isPresent()){
                        throw new RuntimeException("El estado del equipo no existe");
                    }
                    inventarioEquipos1.setIdEstado(estadoEquipo.get().getId());
                }
                return inventarioEquipos1;
            }).collect(Collectors.toList());
            List<InventarioEquipos> inventarioEquiposGuardados = inventarioEquiposRepository.saveAll(inventarioEquipos);
            return inventarioEquiposGuardados.stream().map(InventarioEquipos::getId).collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public List<EstadoEquipo> getListaEstados() throws Exception {
        try{
            List<EstadoEquipo> estados = estadoEquipoRepository.findAll();
            return estados;
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

}
