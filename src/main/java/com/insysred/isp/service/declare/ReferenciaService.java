package com.insysred.isp.service.declare;

import com.insysred.isp.dto.ReferenciaDTO;

public interface ReferenciaService {
    ReferenciaDTO guardar(ReferenciaDTO referenciaDTO) throws Exception;

    ReferenciaDTO actualizar(Long id, ReferenciaDTO referenciaDTO);

    ReferenciaDTO getReferenciaById(Long id) throws Exception;

    ReferenciaDTO getReferenciaByIdCliente(Long id) throws Exception;

}
