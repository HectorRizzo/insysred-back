package com.insysred.isp.service.declare;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.insysred.isp.dto.ArchivoBancoDTO;
import com.insysred.isp.dto.DetalleArchivoBancoDTO;
import com.insysred.isp.entities.ArchivoBanco;

public interface ArchivoBancoService {

	Page<ArchivoBanco> obtenerArchivosConciliacion(PageRequest paginaRequest, String filtro, Instant fechaInicio,
			Instant fechaFin);

	ArchivoBancoDTO obtenerArchivoConciliacionId(Long idArchivo);

	ArchivoBancoDTO saveArchivoConciliacion(ArchivoBancoDTO archivoConciliacionDTO,
			List<DetalleArchivoBancoDTO> detalleArchivo, Long idTipoBanco);
}
