package com.insysred.isp.service.declare;

import java.util.List;

import com.insysred.isp.entities.EstadoContrato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import com.insysred.isp.dto.CambioEstadoContratoDto;
import com.insysred.isp.dto.ContratoDto;
import com.insysred.isp.dto.ContratoNewDto;
import com.insysred.isp.entities.Contrato;

public interface ContratoServide {
    ContratoDto guardarContrato(ContratoNewDto contratoNewDto);

    ContratoDto cmbEstadoContrato(CambioEstadoContratoDto cambioEstadoContratoDto) throws Exception;

    List<ContratoDto> getAllContratos();
    
    Page<Contrato> getAllContratosClientes(PageRequest paginaRequest, String filtro);
    
    ResponseEntity<?> generarReporteContrato(Long idContrato) throws Exception;

    List<EstadoContrato> getEstadosContrato();
}
