package com.insysred.isp.service.declare;

import com.insysred.isp.dto.RepresentanteLegalDto;

public interface RepresentanteLegalService {
    RepresentanteLegalDto guardar(RepresentanteLegalDto representanteLegalDTO);

    RepresentanteLegalDto getByIdentificacion(String identificacion);

}
