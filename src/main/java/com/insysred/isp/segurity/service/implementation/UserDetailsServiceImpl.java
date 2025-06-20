package com.insysred.isp.segurity.service.implementation;

import com.insysred.isp.segurity.models.Usuario;
import com.insysred.isp.segurity.repository.SecurityUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SecurityUsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

//        Collection<? extends GrantedAuthority> authorities = usuario
//                .getUsuarioRoles()
//                .stream()
//                .map(rol -> new SimpleGrantedAuthority("ROLE_".concat(rol.getRol().getNombre())))
//                .collect(Collectors.toSet());

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                true,
                true,
                true,
                true,
                usuario.getAuthorities()
        );
    }
}
