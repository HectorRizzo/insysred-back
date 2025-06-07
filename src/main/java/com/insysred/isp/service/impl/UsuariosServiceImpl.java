package com.insysred.isp.service.impl;

import com.insysred.isp.dto.AsignarEmpleadoDTO;
import com.insysred.isp.dto.ClienteContratoDTO;
import com.insysred.isp.dto.EmpleadosDTO;
import com.insysred.isp.dto.UsuarioDto;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Empleados;
import com.insysred.isp.entities.RolOld;
import com.insysred.isp.entities.UsuarioOld;
import com.insysred.isp.filtros.FiltroCliente;
import com.insysred.isp.filtros.FiltroUsuarios;
import com.insysred.isp.mapper.UsuarioMapper;
import com.insysred.isp.repository.EmpleadosRepository;
import com.insysred.isp.repository.RolRepositoryOld;
import com.insysred.isp.repository.UsuarioRepository;
import com.insysred.isp.repository.UsuarioRepositoryOld;
import com.insysred.isp.segurity.models.Usuario;
import com.insysred.isp.segurity.repository.RolRepository;
import com.insysred.isp.service.declare.UsuarioService;
import jakarta.ejb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuariosServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpleadosRepository empleadosRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private RolRepository rolRepository;


    @Override
    public Page<Usuario> obtenerTodos(PageRequest paginaRequest, String filtro) {
        try {
            Sort sort = Sort.by(Sort.Direction.ASC, "id");
            Long total = usuarioRepository.count(FiltroUsuarios.contieneTexto(filtro));
            Page<Usuario> usuarios = usuarioRepository.findAll(FiltroUsuarios.contieneTexto(filtro), paginaRequest.withSort(sort));
            // Obtén los IDs de los Cliente
            return new PageImpl<>(usuarios.stream().collect(Collectors.toList()), paginaRequest, total);
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return null;
        }
//        return usuarioMapper.toDto(usuarioRepository.findAll());
    }

    @Override
    public UsuarioDto obtenerPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.map(this::convertirAUsuarioDTO).orElse(null);
    }

    @Transactional
    @Override
    public Map<String, String> guardar(AsignarEmpleadoDTO empleadoDTO) throws DuplicateKeyException, Exception {
        Map<String, String> map = new HashMap<>();
        try {
            Optional<Empleados> empleadoOptional = empleadosRepository.findById(empleadoDTO.getIdEmpleado());
            if (!empleadoOptional.isPresent()) {
                throw new Exception("El empleado no existe");
            }
            Optional<Usuario> usuarioOptional = usuarioRepository.findByIdEmpleado(empleadoDTO.getIdEmpleado());
            if (usuarioOptional.isPresent()) {
                throw new DuplicateKeyException("El usuario ya existe");
            }

            Empleados empleado = empleadoOptional.get();
            String username = "";
            String username1 = empleado.getPrimerNombre().toLowerCase().charAt(0) + empleado.getPrimerApellido().toLowerCase();
            String username2 = String.valueOf(empleado.getPrimerNombre().toLowerCase().charAt(0));
            username2 += empleado.getSegundoNombre() != null && !empleado.getSegundoNombre().isEmpty()
                    ? empleado.getSegundoNombre().toLowerCase().charAt(0) : "";
            username2 += empleado.getPrimerApellido().toLowerCase();
            String username3 = empleado.getPrimerNombre().toLowerCase().charAt(0) +
                    empleado.getPrimerApellido().toLowerCase() +
                    empleado.getSegundoApellido().toLowerCase().charAt(0);

            if (!this.usernameExists(username1)) {
                username = username1;
            } else if (!this.usernameExists(username2)) {
                username = username2;
            } else if (!this.usernameExists(username3)) {
                username = username3;
            } else {
                throw new DuplicateKeyException("El username ya existe, intente poner un segundo nombre o segundo apellido");
            }

            String password = this.generateRandomString(11);
            String passwordEncoded = this.generateRandomPassword(password);

            Usuario usuario = new Usuario();
            usuario.setPassword(passwordEncoded);
            usuario.setActivo(true);
            usuario.setFechaCreacion(new Date());
            usuario.setUsername(username);
            usuario.setEsPrimerInicio(true);
            usuario.setActivo(true);
            usuario.setIdEmpleado(empleado.getId());
            usuarioRepository.save(usuario);
            map.put("username", username);
            map.put("password", password);
            return map;
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public UsuarioDto actualizar(Long id, UsuarioDto usuarioDTO, Boolean activo) {
        if (activo != null) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                usuario.setActivo(activo);
                usuarioRepository.save(usuario);
            }
            return usuarioDTO;
        }
       Optional<Usuario> userOptional = usuarioRepository.findById(id);
        if (userOptional.isPresent()) {
            Usuario usuario = userOptional.get();
            usuario.setIdEmpleado(usuarioDTO.getEmpleado() != null ? usuarioDTO.getEmpleado().getId() : usuario.getIdEmpleado());
            usuario.setActivo(usuarioDTO.getActivo() != null ? usuarioDTO.getActivo() : usuario.getActivo());
            usuario.setFechaModificacion(new Date());
            usuarioRepository.save(usuario);
        }
        return usuarioDTO;
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

//    @Override
//    public List<UsuarioDto> obtenerPorScursal(Long codSucursal) {
//        // System.out.println(usuarioRepository.usuarioSucursal(codSucursal));
//        System.out.println(usuarioRepository.findBySucursalesId(codSucursal));
//        return null;//usuarioMapper.toDto(usuarioRepository.usuarioSucursal(codSucursal));
//    }

//    @Override
//    public List<UsuarioDto> obtenerTecn() {
//        return usuarioMapper.toDto(usuarioRepository.findToTecnicos());
//    }

    private UsuarioDto convertirAUsuarioDTO(Usuario usuario) {
        return usuarioMapper.toDto(usuario);
    }

    private Usuario convertirAUsuario(UsuarioDto usuarioDTO) {
        return usuarioMapper.toEntity(usuarioDTO);
    }

    private Boolean usernameExists(String username) throws Exception {
        try {
            Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
            return usuario.isPresent();
        } catch (Exception e) {
            throw new Exception("Error al verificar si el username ya existe: " + e.getMessage());
        }
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        return stringBuilder.toString();
    }


    private String generateRandomPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
        return passwordEncoder.encode(password);
    }

    @Override
    public void actualizarContrasena(Long id, String contrasena) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
            if (usuarioOptional.isPresent() && !contrasena.isEmpty() && usuarioOptional.get().getEsPrimerInicio()) {
                Usuario usuario = usuarioOptional.get();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
                usuario.setPassword(passwordEncoder.encode(contrasena));
                usuario.setEsPrimerInicio(false);
                usuarioRepository.save(usuario);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la contraseña: " + e.getMessage());
        }
    }

    @Override
    public Map<String, String> resetearContrasena(Long id) {
        try {
            Map<String, String> map = new HashMap<>();
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                String password = this.generateRandomString(11);
                String passwordEncoded = this.generateRandomPassword(password);
                usuario.setPassword(passwordEncoded);
                usuario.setEsPrimerInicio(true);
                usuarioRepository.save(usuario);
                map.put("password", password);
                return map;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al resetear la contraseña: " + e.getMessage());
        }
        return null;
    }



}
