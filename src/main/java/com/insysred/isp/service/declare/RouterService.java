package com.insysred.isp.service.declare;

import com.insysred.isp.dto.*;

import java.util.List;

public interface RouterService {
    List<RoutersDto> getAllRouter();
    RoutersDto saveRouter(RouterNewDto routersDto);

    RoutersDto getRouter(Long id);

    List<RoutersDto> getRouterSucursal(Long idSucursal);

    RoutersDto cambiarEstadoRouter(CambioEstadoDto cambioEstadoDto);

    RoutersDto asignarSucursal(AsignaSucursalDto asignaSucursalDto);

    RoutersDto updateRouter(Long id, RouterNewDto routerNewDto);
    RoutersDto resetPassRouter(ResetPasswordDto resetPasswordDto);
}
