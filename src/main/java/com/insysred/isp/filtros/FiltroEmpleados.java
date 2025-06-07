package com.insysred.isp.filtros;

import com.insysred.isp.entities.Empleados;
import com.insysred.isp.entities.Factura;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FiltroEmpleados {

    public static Specification<Empleados> contieneTexto(String filtro, Boolean estado) {
        return (root, query, builder) -> {
            Predicate predicate;

            if (estado) {
                predicate = builder.equal(root.get("activo"), estado);
            } else {
                predicate = builder.not(builder.equal(root.get("activo"), estado));
            }

            return builder.and(
                    builder.or(
                            builder.like(builder.lower(root.get("nombreCompleto")), "%" + filtro.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("numeroIdentificacion")), "%" + filtro.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("correo")), "%" + filtro.toLowerCase() + "%")),
                    builder.and(predicate));


        };
    }
}
