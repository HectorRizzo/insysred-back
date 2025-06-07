package com.insysred.isp.filtros;

import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Empleados;
import com.insysred.isp.segurity.models.Usuario;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class FiltroUsuarios {
    public static Specification<Usuario> contieneTexto(String filtro) {
        return (root, query, builder) -> {
            Predicate predicate;
            Predicate activo = builder.isTrue(root.get("activo"));
            if (filtro != null && !filtro.isEmpty()) {
                predicate = builder.or(
                        builder.like(builder.lower(root.get("username")), "%" + filtro.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("nombres")), "%" + filtro.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("apellidos")), "%" + filtro.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("email")), "%" + filtro.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("identificacion")), "%" + filtro.toLowerCase() + "%")
                );
            } else {
                predicate = builder.isNotNull(root.get("id"));
            }

            return builder.and(predicate, activo);
        };
    }
}
