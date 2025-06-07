package com.insysred.isp.service.impl;

import com.insysred.isp.dto.*;
import com.insysred.isp.entities.Empresa;
import com.insysred.isp.entities.Sucursales;
import com.insysred.isp.entities.UsuarioSucursales;
import com.insysred.isp.mapper.SucursalMapper;
import com.insysred.isp.repository.ClienteSucursalService;
import com.insysred.isp.repository.EmpresaRepository;
import com.insysred.isp.repository.SucursalRepository;
import com.insysred.isp.repository.UsuarioSucursalesRepository;
import com.insysred.isp.service.declare.SucursalService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class SucursalServiceImpl implements SucursalService {

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(SucursalServiceImpl.class);
  @Autowired
  private SucursalRepository sucursalRepository;

  @Autowired
  private EmpresaRepository empresaRepository;

  @Autowired
  private SucursalMapper sucursalMapper;

  @Autowired
  private ClienteSucursalService clienteSucursalService;

  @Autowired
    private UsuarioSucursalesRepository usuarioSucursalesRepository;

  private static Logger logger = Logger.getLogger(SucursalServiceImpl.class.getName());
  @Override
  public List<SucursalDto> obtenerTodos() {
    return sucursalMapper.toDto(sucursalRepository.findAll());
  }

  @Override
  public List<SucursalDto> obtenerActivos() {
    return sucursalMapper.toDto(sucursalRepository.getSucursalByActive());
  }

  @Override
  public List<SucursalDto> obtenerPorUsuario(Long idUsuario) {
    return sucursalMapper.toDto(sucursalRepository.getSucursalesByUsuarioId(idUsuario));
  }

  @Override
  @Transactional
  public SucursalDto getSucursalById(Long id) {
    System.out.println("-----------------------");
    System.out.println(id);
    System.out.println("-----------------------");
    // System.out.println(sucursalRepository.getReferenceById(id));
    System.out.println(sucursalRepository.findById(id));
    return null;
  }

  @Override
  public SucursalDto guardar(SucursalDto sucursalDTO) {
    Sucursales sucursales = new Sucursales();
    sucursales.setNombre(sucursalDTO.getNombre());
    sucursales.setDireccion(sucursalDTO.getDireccion());
    sucursales.setEstablecimiento(sucursalDTO.getEstablecimiento());
    sucursales.setPuntoEmision(sucursalDTO.getPuntoEmision());
    sucursales.setSecuencial(sucursalDTO.getSecuencial());
    sucursales.setIsActive(true);
    Optional<Empresa> empresaOptional = empresaRepository.findById(sucursalDTO.getEmpresa().getId());
    empresaOptional.ifPresent(sucursales::setEmpresa);
    Sucursales sucursalesSave = sucursalRepository.save(sucursales);
    return convertirSucursalDTO(sucursalesSave);
  }

  @Override
  public SucursalDto actualizar(Long id, SucursalDto sucursalDTO) {
    Sucursales sucursales = sucursalRepository.getReferenceById(id);
    sucursales.setIsActive(sucursalDTO.getIsActive());
    sucursales.setNombre(sucursalDTO.getNombre());
    sucursales.setEstablecimiento(sucursalDTO.getEstablecimiento());
    sucursales.setPuntoEmision(sucursalDTO.getPuntoEmision());
    sucursales.setSecuencial(sucursalDTO.getSecuencial());
    sucursales.setDireccion(sucursalDTO.getDireccion());
    return sucursalMapper.toDto(sucursalRepository.save(sucursales));
  }

  @Override
  public List<SucursalClienteDto> getSucCliente(Long idCliente) {
    return clienteSucursalService.obtenerClienteSucursales(idCliente);
  }

  @Override
  public void asignarSucursalesCliente(AsignarSucursalClienteDto asignarSucursalClienteDto) {
    List<SucursalClienteDto> sucursalClienteDtoList = clienteSucursalService.obtenerClienteSucursales(asignarSucursalClienteDto.getIdCliente());
    List<SucursalClienteDto> sobrantes = new ArrayList<>();
    List<SucursalClienteDto> correctos = new ArrayList<>();
    for (int j = 0; j < sucursalClienteDtoList.size(); j++) {
      boolean found = false;
      for (int i = 0; i < asignarSucursalClienteDto.getIdSucursal().size(); i++) {
        if (asignarSucursalClienteDto.getIdSucursal().get(i) == sucursalClienteDtoList.get(j).getId()) {
          found = true;
          correctos.add(sucursalClienteDtoList.get(j));
          break;
        }
      }
      if (!found) {
        sobrantes.add(sucursalClienteDtoList.get(j));
      }
    }
    for (SucursalClienteDto sucursalClienteDto : sobrantes) {
      Boolean existeAsignacion = sucursalRepository.estaAsignado(asignarSucursalClienteDto.getIdCliente(), sucursalClienteDto.getId());
      if (existeAsignacion) {
        sucursalRepository.eliminaAsignacion(asignarSucursalClienteDto.getIdCliente(), sucursalClienteDto.getId());
      }
    }

    for (SucursalClienteDto sucursalClienteDto : sobrantes) {
      Boolean existeAsignacion = sucursalRepository.estaAsignado(asignarSucursalClienteDto.getIdCliente(), sucursalClienteDto.getId());
      if (existeAsignacion) {
        sucursalRepository.eliminaAsignacion(asignarSucursalClienteDto.getIdCliente(), sucursalClienteDto.getId());
      }
    }

    for (SucursalClienteDto sucursalClienteDto : correctos) {
      Boolean existeAsignacion = sucursalRepository.estaAsignado(asignarSucursalClienteDto.getIdCliente(), sucursalClienteDto.getId());
      if (!existeAsignacion) {
        sucursalRepository.crearAsignacion(asignarSucursalClienteDto.getIdCliente(), sucursalClienteDto.getId());
      }
    }
  }

  private SucursalDto convertirSucursalDTO(Sucursales sucursal) {
    return sucursalMapper.toDto(sucursal);
  }

  @Override
  public void asignarSucursalesUsuario(AsignarSucursalUsuarioDTO asignarSucursalUsuarioDto) {
    logger.info("Asignando sucursales a usuario");
    logger.info("Usuario: " + asignarSucursalUsuarioDto.toString());
    Long idUsuario = asignarSucursalUsuarioDto.getIdUsuario();
    List<SucursalesXAsignarDTO> sucursales = asignarSucursalUsuarioDto.getSucursales();
    List<UsuarioSucursales> usuarioSucursalesList = usuarioSucursalesRepository.findByUsuarioId(asignarSucursalUsuarioDto.getIdUsuario());
    for (int i = 0; i < usuarioSucursalesList.size(); i++) {
      int finalI = i;
      SucursalesXAsignarDTO sucursal = sucursales.stream().filter(s -> s.getId().equals(usuarioSucursalesList.get(finalI).getSucursalId())).findFirst().orElse(null);
      if (Objects.nonNull(sucursal)) {
        UsuarioSucursales usuarioSucursales = usuarioSucursalesList.get(i);
        usuarioSucursales.setActivo(sucursal.getChecked());
        usuarioSucursales.setFechaModificacion(new Date());
        usuarioSucursalesRepository.save(usuarioSucursales);
        sucursales.remove(sucursal);
      }
    }
    for (SucursalesXAsignarDTO sucursal : sucursales) {
      UsuarioSucursales usuarioSucursales = new UsuarioSucursales();
      logger.info("Sucursal: " + sucursal.getId());
      logger.info("Usuario: " + idUsuario);
      logger.info("Activo: " + sucursal.getChecked());
      usuarioSucursales.setUsuarioId(idUsuario);
      usuarioSucursales.setSucursalId(sucursal.getId());
      usuarioSucursales.setActivo(sucursal.getChecked());
      usuarioSucursales.setFechaIngreso(new Date());
      usuarioSucursalesRepository.save(usuarioSucursales);
    }
  }

  @Override
  public List<SucursalesXAsignarDTO> getSucursalesAsignadas(Long idUsuario) throws Exception {
    List<SucursalesXAsignarDTO> sucursales = new ArrayList<>();
   try{
     List<UsuarioSucursales> usuarioSucursalesList = usuarioSucursalesRepository.findByUsuarioId(idUsuario);
     if(Objects.nonNull(usuarioSucursalesList) && !usuarioSucursalesList.isEmpty()) {
       usuarioSucursalesList.forEach(usuarioSucursales -> {
         SucursalesXAsignarDTO sucursal = new SucursalesXAsignarDTO();
         sucursal.setId(usuarioSucursales.getSucursalId());
         sucursal.setChecked(usuarioSucursales.getActivo());
         sucursal.setNombre(sucursalRepository.findById(usuarioSucursales.getSucursalId()).get().getNombre());
         sucursales.add(sucursal);
       });
     }
     }catch (Exception e){
     throw  new Exception(e.getMessage());
   }
    return sucursales;
  }
}
