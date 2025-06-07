package com.insysred.isp.filtros;

import com.insysred.isp.entities.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class FiltroPermisos {

    public static Specification<PermisosXRoles> contieneTexto(String filtro) {
        return (root, query, builder) -> {
            Join<PermisosXRoles, Modulos> modulos = root.join("idModulo");
            Join<PermisosXRoles, RolOld> rol = root.join("idRol");

            return builder.and(builder.or(
                    builder.like(builder.lower(modulos.get("nombreModulo")), "%" + filtro.toLowerCase() + "%"),
                    builder.like(builder.lower(rol.get("nombre")), "%" + filtro.toLowerCase() + "%"))
            );
        };

    }

}
