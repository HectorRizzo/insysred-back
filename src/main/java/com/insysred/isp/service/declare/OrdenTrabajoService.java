package com.insysred.isp.service.declare;

import com.insysred.isp.dto.OrdenAtencionNewDto;
import com.insysred.isp.dto.OrdenTrabajoDto;
import com.insysred.isp.dto.OrdenTrabajoNewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrdenTrabajoService {
    Page<OrdenTrabajoDto> getAllOrdenTrabajo(Pageable pageable, String filtro);

    Page<OrdenTrabajoDto> getAllOrdenTrabajoByTecnico(Pageable pageable, Long idTecnico, String filtro);

    OrdenTrabajoDto guardar(OrdenTrabajoNewDto ordenTrabajoDTO) throws Exception;

    Boolean tratarOrdenAtencion(OrdenAtencionNewDto ordenAtencionDto);

    void actualizar(Long id, OrdenTrabajoNewDto ordenTrabajoDTO) throws Exception;

    void inactivarOrdenTrabajo(Long id) throws Exception;

}
