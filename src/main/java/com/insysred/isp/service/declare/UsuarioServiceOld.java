package com.insysred.isp.service.declare;

import com.insysred.isp.dto.UsuarioDto;
import java.util.List;
public interface UsuarioServiceOld {

    List<UsuarioDto> obtenerTodos();

    UsuarioDto obtenerPorId(Long id);

    UsuarioDto guardar(UsuarioDto usuarioDTO);
    UsuarioDto actualizar(Long id, UsuarioDto usuarioDTO);

    void eliminar(Long id);

    List<UsuarioDto> obtenerPorScursal(Long codSucursal);

    List<UsuarioDto> obtenerTecn();

}
