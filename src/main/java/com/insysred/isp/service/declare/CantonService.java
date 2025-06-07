package com.insysred.isp.service.declare;

import com.insysred.isp.dto.CantonDto;
import java.util.List;

public interface CantonService {

    List<CantonDto> lstCanton();

    List<CantonDto> getCantonByProvincia(Long idProvincia);

    CantonDto getCantonById(Long idCanton);

    CantonDto saveCanton(CantonDto cantonDTO);
}
