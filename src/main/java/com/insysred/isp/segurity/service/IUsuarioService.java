package com.insysred.isp.segurity.service;


import com.insysred.isp.segurity.models.Usuario;

import java.util.List;
import java.util.Map;

public interface IUsuarioService {
    List<Map<String, Object>> obtenerPermisos(Long idUsuario, Long idSucursal) throws Exception;
}
