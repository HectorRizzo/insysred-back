package com.insysred.isp.service.declare;

import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.PlanesDto;
import com.insysred.isp.dto.PlanesNewDto;
import com.insysred.isp.entities.Planes;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PlanesService {

    Page<Planes> obtenerPlanes(PageRequest paginaRequest, Long idSucursal, String filtro, String estado);

    PlanesDto guardarPlan(PlanesNewDto planesDto);

    PlanesDto actualizarPlan(Long id, PlanesNewDto planesNewDto);

    PlanesDto cmbEstadoPlan(CambioEstadoDto cambioEstadoDto);
}
