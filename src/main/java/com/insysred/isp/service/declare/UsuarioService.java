package com.insysred.isp.service.declare;

import com.insysred.isp.dto.AsignarEmpleadoDTO;
import com.insysred.isp.dto.EmpleadosDTO;
import com.insysred.isp.dto.UsuarioDto;
import com.insysred.isp.segurity.models.Usuario;
import jakarta.ejb.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface UsuarioService {

    Page<Usuario> obtenerTodos(PageRequest paginaRequest, String filtro);

    UsuarioDto obtenerPorId(Long id);

    Map<String, String> guardar(AsignarEmpleadoDTO empleado) throws DuplicateKeyException, Exception;
    UsuarioDto actualizar(Long id, UsuarioDto usuarioDTO, Boolean activo);

    void eliminar(Long id);

    void actualizarContrasena(Long id, String contrasena);

    Map<String, String> resetearContrasena(Long id) throws Exception;

//    List<UsuarioDto> obtenerPorScursal(Long codSucursal);
//
//    List<UsuarioDto> obtenerTecn();
}
