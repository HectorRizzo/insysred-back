package com.insysred.isp.service.declare;

import com.insysred.isp.dto.InventarioEquipoDTO;
import com.insysred.isp.entities.EstadoEquipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface InventarioEquiposService {

    Page<InventarioEquipoDTO> obtenerInventarioEquipos(PageRequest paginaRequest,
                                                        Integer factura,
                                                       Long idMarcaEquipo,
                                                        String fechaDesde,
                                                        String fechaHasta,
                                                        String ip,
                                                        String macAddress,
                                                        String modoOperacion,
                                                        String estado,
                                                        String activo) throws Exception;

    InventarioEquipoDTO guardar(Integer factura,
                                Long idMarcaEquipo,
                                String fechaCompra,
                                String ip,
                                String macAddress,
                                String modoOperacion,
                                String estado) throws Exception;

    InventarioEquipoDTO actualizar(Long id, InventarioEquipoDTO inventarioEquipoDTO);

    InventarioEquipoDTO getById(Long idInventarioEquipo);

    List<Long> guardarInventarioEquipos(List<InventarioEquipoDTO> inventarioEquipoDTO) throws Exception;
    List<EstadoEquipo> getListaEstados() throws Exception;
}
