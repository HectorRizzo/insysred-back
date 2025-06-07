package com.insysred.isp.service.impl;

import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.PlanesDto;
import com.insysred.isp.dto.PlanesNewDto;
import com.insysred.isp.entities.Planes;
import com.insysred.isp.entities.Sucursales;
import com.insysred.isp.filtros.FiltroPlanes;
import com.insysred.isp.mapper.PlanesMapper;
import com.insysred.isp.repository.PlanesRepository;
import com.insysred.isp.repository.SucursalRepository;
import com.insysred.isp.service.declare.MikroTikService;
import com.insysred.isp.service.declare.PlanesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PlanesServiceImpl implements PlanesService {
  @Autowired
  private PlanesRepository planesRepository;

  @Autowired
  private PlanesMapper planesMapper;

  @Autowired
  private SucursalRepository sucursalRepository;

  @Autowired
  private MikroTikService mikroTikService;

  @Override
  public Page<Planes> obtenerPlanes(PageRequest paginaRequest, Long idSucursal, String filtro, String estado) {
    Sort sort = Sort.by("id").ascending();
    paginaRequest.withSort(sort);
    switch (estado) {
      case "t":
        return planesRepository.findAll(FiltroPlanes.contieneTexto(filtro, idSucursal),paginaRequest);
      case "a":
        return planesRepository.findByStatus(true, paginaRequest);
      case "i":
        return planesRepository.findByStatus(false, paginaRequest);
      default:
        System.out.println("Opción invalida");
        return null;
    }
  }

  @Override
  public PlanesDto guardarPlan(PlanesNewDto planesDto) {
    Planes planes = new Planes();
    planes.setName(planesDto.getName());
    planes.setDescripcion(planesDto.getDescripcion());
    planes.setStatus(true);
    planes.setMegabytes(planesDto.getMegabytes());
    planes.setPrice(planesDto.getPrice());
    Sucursales sucursales = sucursalRepository.getReferenceById(planesDto.getSucursales());
    planes.setSucursales(sucursales);
    PlanesDto planesDto1 = planesMapper.toDto(planesRepository.save(planes));
    //mikroTikService.newQueue(planesDto1);
    return planesDto1;
  }

  @Override
  public PlanesDto actualizarPlan(Long id, PlanesNewDto planesNewDto) {
    Planes planes = planesRepository.getReferenceById(id);
    planes.setName(planesNewDto.getName());
    planes.setDescripcion(planesNewDto.getDescripcion());
    planes.setMegabytes(planesNewDto.getMegabytes());
    planes.setPrice(planesNewDto.getPrice());
    Sucursales sucursales = sucursalRepository.getReferenceById(planesNewDto.getSucursales());
    planes.setSucursales(sucursales);
    return planesMapper.toDto(planesRepository.save(planes));
  }

  @Override
  public PlanesDto cmbEstadoPlan(CambioEstadoDto cambioEstadoDto) {
    Planes planes = planesRepository.getReferenceById(cambioEstadoDto.getIdComponent());
    planes.setStatus(cambioEstadoDto.getEstatus());
    return planesMapper.toDto(planesRepository.save(planes));
  }

}
