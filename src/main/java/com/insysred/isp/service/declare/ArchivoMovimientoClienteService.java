package com.insysred.isp.service.declare;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import com.insysred.isp.dto.ArchivoMovimientoClienteContratoDetDTO;
import com.insysred.isp.dto.ArchivoMovimientoClienteDTO;
import com.insysred.isp.dto.ArchivoMovimientoClienteUltimosDTO;
import com.insysred.isp.dto.ConciliarArchivoMovimientoClienteDTO;
import com.insysred.isp.dto.ExistenciaMovClienteXMovBancoDTO;
import com.insysred.isp.entities.ArchivoMovimientoCliente;

public interface ArchivoMovimientoClienteService {

	List<ArchivoMovimientoClienteContratoDetDTO> obtenerContratosCliente(Long idCliente) throws Exception;

	List<ArchivoMovimientoClienteUltimosDTO> obtenerArchivosMovimientoClienteUltimos(Long idCliente) throws Exception;

	Page<ArchivoMovimientoCliente> obtenerArchivosMovimientoCliente(PageRequest paginaRequest, String filtro,
			Instant fechaInicio, Instant fechaFin);

	ArchivoMovimientoClienteDTO obtenerArchivoMovimientoClienteId(Long idArchivo);

	ExistenciaMovClienteXMovBancoDTO validarComprobanteMovClienteXMovBanco(Long numeroComprobante);

	ArchivoMovimientoClienteDTO saveArchivoMovimientoCliente(
			ConciliarArchivoMovimientoClienteDTO validarArchivoMovimientoCliente);

	void uploadArchivoMovimientoCliente(Long idCliente, Long idContrato, MultipartFile archivos) throws Exception;

}
