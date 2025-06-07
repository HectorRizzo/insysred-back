package com.insysred.isp.filtros;

import com.insysred.isp.entities.InventarioEquipos;
import com.insysred.isp.entities.MarcaEquipos;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import jakarta.persistence.criteria.Predicate;

public class FiltroInventarioEquipos {

    public static Specification<InventarioEquipos> contieneDatos(Integer facturaCompra, Instant fechaDesde, Instant fechaHasta, String ip, String macAddress, MarcaEquipos marcaEquipos, String modoOperacion, String estado, String activo) {
        return (root, query, builder) -> {
            //verificar si los campos son nulos, si no son nulos se agrega al filtro
            Predicate predicateFacturaCompra;
            Predicate predicateFechaCompra;
            Predicate predicateIp;
            Predicate predicateMacAddress;
            Predicate predicateMarcaEquipos;
            Predicate predicateModoOperacion;
            Predicate predicateEstado;
            Predicate predicateActivo;

            if(ObjectUtils.isNotEmpty(facturaCompra)){
                predicateFacturaCompra = builder.equal(root.get("facturaCompra"), facturaCompra);
            }else{
                predicateFacturaCompra = builder.isNotNull(root.get("id"));
            }

            if(ObjectUtils.isNotEmpty(fechaDesde) && ObjectUtils.isNotEmpty(fechaHasta)){
                predicateFechaCompra = builder.between(root.get("fechaCompra"), fechaDesde, fechaHasta);
            }else{
                predicateFechaCompra = builder.isNotNull(root.get("id"));
            }

            if(ObjectUtils.isNotEmpty(ip)){
                predicateIp = builder.equal(root.get("ip"), ip);
            }else{
                predicateIp = builder.isNotNull(root.get("id"));
            }

            if(ObjectUtils.isNotEmpty(macAddress)){
                predicateMacAddress = builder.equal(root.get("macAddress"), macAddress);
            }else{
                predicateMacAddress = builder.isNotNull(root.get("id"));
            }

            if(ObjectUtils.isNotEmpty(marcaEquipos)){

                predicateMarcaEquipos = builder.equal(root.get("marcaEquipos"), marcaEquipos);
            }else{
                predicateMarcaEquipos = builder.isNotNull(root.get("id"));
            }

            if(ObjectUtils.isNotEmpty(modoOperacion)){
                predicateModoOperacion = builder.equal(root.get("modoOperacion"), modoOperacion);
            }else{
                predicateModoOperacion = builder.isNotNull(root.get("id"));
            }

            if(ObjectUtils.isNotEmpty(estado)){
                predicateEstado = builder.equal(root.get("idEstado"), estado);
            }else{
                predicateEstado = builder.isNotNull(root.get("id"));
            }

            if(ObjectUtils.isNotEmpty(activo) && !activo.equals("TODOS")){
                predicateActivo = builder.equal(root.get("activo"), activo.equals("ACTIVO")? true :
                        activo.equals("INACTIVO")? false : null);
            }else{
                predicateActivo = builder.isNotNull(root.get("id"));
            }


            return builder.and(predicateFacturaCompra, predicateFechaCompra, predicateIp, predicateMacAddress, predicateMarcaEquipos, predicateModoOperacion, predicateEstado , predicateActivo);
        };
    }
}
