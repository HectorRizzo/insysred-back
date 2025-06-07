package com.insysred.isp.repository;

import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Sucursales;
import com.insysred.isp.segurity.models.Usuario;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario>{

    @Query("SELECT u FROM Usuario u WHERE u.username = ?1")
    Optional<Usuario> findByUsername(String username);

    @Query("SELECT count(u) FROM Usuario u")
    Long countByUsername(Specification<Usuario> spec);

    @Query("SELECT u.sucursales FROM Usuario u Where u.id =?1")
    List<Sucursales> obtenerSucursalesXUsuario(Long id);

    @Query("SELECT u FROM Usuario u WHERE u.idEmpleado = ?1")
    Optional<Usuario> findByIdEmpleado(Long idEmpleado);

}
