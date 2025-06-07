package com.insysred.isp.segurity.service.implementation;

import com.insysred.isp.entities.Modulos;
import com.insysred.isp.repository.PermisosRepository;
import com.insysred.isp.segurity.repository.SecurityUsuarioRepository;
import com.insysred.isp.segurity.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final SecurityUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private static Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());
    @Autowired
    private PermisosRepository permisosRepository;


    @Override
    public List<Map<String, Object>> obtenerPermisos(Long idUsuario, Long idSucursal) throws Exception {
        List<Map<String, Object>> permisos = new ArrayList<>();
        try {
            List<Modulos> modulos = permisosRepository.getModulosByUsuario(idUsuario, idSucursal);
            Map<Object, Map<String, Object>> permisosModuloPadre = new HashMap<>();

            // Construir el árbol de módulos recursivamente
            for (Modulos modulo : modulos) {
                if (modulo.getPadre() == null) {
                    Map<String, Object> modPadre = construirArbol(modulo, permisosModuloPadre, idUsuario, idSucursal);
                    permisos.add(modPadre);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los permisos" + e.getMessage());
            throw new Exception("Error al obtener los permisos " + e.getMessage());
        }
        return permisos;
    }

    // Función recursiva para construir el árbol de módulos
    private Map<String, Object> construirArbol(Modulos modulo, Map<Object, Map<String, Object>> permisosModuloPadre, Long idUsuario, Long idSucursal) {
        Map<String, Object> modPadre = new HashMap<>();
        modPadre.put("id", modulo.getId());
        modPadre.put("route", modulo.getRuta());
        modPadre.put("name", modulo.getNombreModulo());
        String type = modulo.getPadre() == null ? "sub" : "link";
        modPadre.put("type", type);
        modPadre.put("children", new ArrayList<Map<String, Object>>());

        permisosModuloPadre.put(modulo.getId(), modPadre);

        // Construir los hijos de manera recursiva
        List<Modulos> hijos = obtenerHijos(modulo.getId(), idUsuario, idSucursal);
        for (Modulos hijo : hijos) {
            Map<String, Object> modHijo = construirArbol(hijo, permisosModuloPadre, idUsuario, idSucursal);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> children = (List<Map<String, Object>>) modPadre.get("children");
            children.add(modHijo);
        }

        return modPadre;
    }

    private List<Modulos> obtenerHijos(Long idPadre,Long idUsuario,Long idSucursal) {
        List<Modulos> modulos = permisosRepository.getModulosByUsuario(idUsuario, idSucursal);
        List<Modulos> hijos = new ArrayList<>();
        for (Modulos modulo : modulos) {
            if (modulo.getPadre() != null && modulo.getPadre().equals(idPadre)) {
                hijos.add(modulo);
            }
        }
        return hijos;
    }

}
