package com.insysred.isp.segurity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insysred.isp.entities.Empleados;
import com.insysred.isp.entities.Sucursales;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
public class Usuario implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private Boolean activo;
    private Boolean esPrimerInicio;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaModificacion;

    @Column(name = "id_empleado")
    private Long idEmpleado;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_sucursales",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sucursal_id", referencedColumnName = "id")
    )
    private List<Sucursales> sucursales;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", referencedColumnName = "id", insertable = false, updatable = false)
    private Empleados empleado;


    @PrePersist
    public void prePersist() {
        fechaCreacion = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UsuarioRol getUsuarioRoles(){
        return null;
    }
}
