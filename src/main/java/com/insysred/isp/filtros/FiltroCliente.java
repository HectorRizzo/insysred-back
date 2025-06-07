package com.insysred.isp.filtros;

import com.insysred.isp.entities.Cliente;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FiltroCliente {
    public static Specification<Cliente> contieneTexto(String filtro, Long idSucursal) {
        return (root, query, builder) -> {
            Predicate predicate;
            Predicate sucursalesPredicate;

            if (filtro != null && !filtro.isEmpty()) {
                predicate = builder.or(
                        builder.like(builder.lower(root.get("identificacion")), "%" + filtro.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("nombres")), "%" + filtro.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("apellidos")), "%" + filtro.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("razonSocial")), "%" + filtro.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("email")), "%" + filtro.toLowerCase() + "%")
                );
            } else {
                predicate = builder.isNotNull(root.get("id"));
            }
            if (idSucursal != null && idSucursal > 0) {
                sucursalesPredicate = builder.equal(root.get("sucursales").get("id"), idSucursal);
                return builder.and(predicate, sucursalesPredicate);
            } else {
                return builder.and(predicate);

            }


        };
    }
}
