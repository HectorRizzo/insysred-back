package com.insysred.isp.service.declare;

import com.insysred.isp.dto.MarcaEquiposDto;
import com.insysred.isp.entities.MarcaEquipos;
import org.springframework.data.domain.Page;

import java.util.List;
public interface MarcaEquiposService {

    List<MarcaEquiposDto> lstMarcaEquipos();

    MarcaEquiposDto guardar(MarcaEquiposDto marcaEquiposDto);

    MarcaEquiposDto actualizar(Long id, MarcaEquiposDto marcaEquiposDto);

    Page<MarcaEquipos> lstMarcaEquiposPaginacion(int pagina, int cantidad, String filtro);
}
