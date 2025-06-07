package com.insysred.isp.service.declare;

import com.insysred.isp.dto.*;

import java.util.List;

public interface SucursalService {

    List<SucursalDto> obtenerTodos();

    List<SucursalDto> obtenerActivos();

    List<SucursalDto> obtenerPorUsuario(Long idUsuario);

    SucursalDto getSucursalById(Long id);

    SucursalDto guardar(SucursalDto sucursalDTO);

    SucursalDto actualizar(Long id, SucursalDto sucursalDTO);

  List<SucursalClienteDto> getSucCliente(Long idCliente);

  void asignarSucursalesCliente(AsignarSucursalClienteDto asignarSucursalClienteDto);
  void asignarSucursalesUsuario(AsignarSucursalUsuarioDTO asignarSucursalUsuarioDto);
  List<SucursalesXAsignarDTO> getSucursalesAsignadas(Long idUsuario) throws Exception;
}
