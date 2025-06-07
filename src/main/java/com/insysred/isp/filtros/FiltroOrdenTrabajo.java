package com.insysred.isp.filtros;

import com.insysred.isp.entities.Modulos;
import com.insysred.isp.entities.OrdenTrabajo;
import com.insysred.isp.entities.PermisosXRoles;
import com.insysred.isp.entities.RolOld;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FiltroOrdenTrabajo {

    public static Specification<OrdenTrabajo> contieneTexto(String filtro) {

        return (root, query, builder) -> {
            Predicate predicateActivo = builder.equal(root.get("esActivo"), true);
            Predicate predicateFiltro = builder.or(
                    builder.like(builder.lower(root.get("id").as(String.class)), "%" + filtro.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("codigo").as(String.class)), "%" + filtro.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("numeroOrden").as(String.class)), "%" + filtro.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("codigoCliente").get("nombres").as(String.class)), "%" + filtro.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("codigoCliente").get("apellidos").as(String.class)), "%" + filtro.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("codigoCliente").get("identificacion").as(String.class)), "%" + filtro.toLowerCase() + "%")
            );
            return builder.and(predicateActivo, predicateFiltro);
        };
    }

    public static Specification<OrdenTrabajo> ordenesActivas() {
        return (root, query, builder) -> {
            return builder.equal(root.get("esActivo"), true);
        };
    }
}
