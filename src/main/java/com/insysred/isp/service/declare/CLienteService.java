package com.insysred.isp.service.declare;


import com.insysred.isp.dto.ClienteContratoDTO;
import com.insysred.isp.dto.ClienteDto;
import com.insysred.isp.dto.ClienteSucursalDto;
import com.insysred.isp.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CLienteService {
    ClienteDto guardar(ClienteDto clienteDTO, Long idSucursal) throws Exception;

    ClienteDto actualizar(Long id, ClienteDto clienteDTO);
    List<ClienteDto> listaClientes();

    ClienteDto getById(Long idCLiente);

    Page<ClienteContratoDTO> obtenerClientes(PageRequest paginaRequest, String filtro, Long idSucursal);

    Boolean agregarSucursal(ClienteSucursalDto clienteSucursalDto);

    List<ClienteDto> getBySycursal(Long idSucursal);

    Boolean verificarCliente(String idCliente) throws Exception;
}
