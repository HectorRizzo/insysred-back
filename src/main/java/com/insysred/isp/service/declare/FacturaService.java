package com.insysred.isp.service.declare;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import com.insysred.isp.dto.AnularFacturaDto;
import com.insysred.isp.dto.AplicarDescuentoFacturaDto;
import com.insysred.isp.dto.CambioEstadoFacturaDto;
import com.insysred.isp.dto.FacturaDTO;
import com.insysred.isp.entities.Factura;

public interface FacturaService {

	List<FacturaDTO> listaFactura();

	Page<Factura> obtenerFacturas(PageRequest paginaRequest, String filtro, Instant fechaInicio, Instant fechaFin, String estado);

	FacturaDTO obtenerFacturaId(Long idFactura);

	FacturaDTO cmbEstadoFactura(CambioEstadoFacturaDto cambioEstadoFacturaDto);

	FacturaDTO anularFactura(AnularFacturaDto anularFacturaDto);
	
	FacturaDTO aplicarDescuento(AplicarDescuentoFacturaDto aplicaDescuentoFacturaDto);
	
	ResponseEntity<?> generarReporteRecibo(Long idRecibo) throws Exception;

}
