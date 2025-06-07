package com.insysred.isp.filtros;

import com.insysred.isp.entities.Planes;
import org.springframework.data.jpa.domain.Specification;

public class FiltroPlanes {

    public static Specification<Planes> contieneTexto( String filtro, Long idSucursal) {
        return (root, query, builder) ->
                builder.and(
                        builder.or(
                                builder.like(builder.lower(root.get("name")), "%" + filtro.toLowerCase() + "%"),
                                builder.like(builder.lower(root.get("descripcion")), "%" + filtro.toLowerCase() + "%")
                        ),
                        builder.equal(root.get("sucursales").get("id"), idSucursal)
                );
    }

}
