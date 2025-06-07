package com.insysred.isp.service.impl;

import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.ArchivoBancoDTO;
import com.insysred.isp.dto.DetalleArchivoBancoDTO;
import com.insysred.isp.entities.ArchivoBanco;
import com.insysred.isp.entities.ArchivoMovimientoCliente;
import com.insysred.isp.entities.DetalleArchivoBanco;
import com.insysred.isp.entities.DetalleErrorArchivoBanco;
import com.insysred.isp.entities.Factura;
import com.insysred.isp.entities.MovClienteXMovBanco;
import com.insysred.isp.entities.Recibo;
import com.insysred.isp.enums.EstadoCarga;
import com.insysred.isp.enums.EstadoConciliacion;
import com.insysred.isp.enums.EstadoFactura;
import com.insysred.isp.enums.FormaPago;
import com.insysred.isp.enums.TipoPago;
import com.insysred.isp.filtros.FiltroArchivoBanco;
import com.insysred.isp.mapper.ArchivoBancoMapper;
import com.insysred.isp.mapper.DetalleArchivoBancoMapper;
import com.insysred.isp.mapper.DetalleErrorArchivoBancoMapper;
import com.insysred.isp.repository.ArchivoBancoRepository;
import com.insysred.isp.repository.ArchivoMovimientoClienteRepository;
import com.insysred.isp.repository.DetalleArchivoBancoRepository;
import com.insysred.isp.repository.DetalleErrorArchivoBancoRepository;
import com.insysred.isp.repository.FacturaRepository;
import com.insysred.isp.repository.MovClienteXMovBancoRepository;
import com.insysred.isp.repository.ReciboRepository;
import com.insysred.isp.repository.TipoBancoRepository;
import com.insysred.isp.service.declare.ArchivoBancoService;

@Service
public class ArchivoBancoServiceImpl implements ArchivoBancoService {

	@Autowired
	private ArchivoBancoRepository archivoConciliacionRepository;

	@Autowired
	private ArchivoBancoMapper archivoConciliacionMapper;

	@Autowired
	private DetalleArchivoBancoRepository detalleArchivoConciliacionRepository;

	@Autowired
	private DetalleArchivoBancoMapper detalleArchivoConciliacionMapper;

	@Autowired
	private DetalleErrorArchivoBancoRepository detalleErrorArchivoConciliacionRepository;

	@Autowired
	private DetalleErrorArchivoBancoMapper detalleErrorArchivoConciliacionMapper;

	@Autowired
	private TipoBancoRepository tipoBancoRepository;
	
	@Autowired
	private ArchivoMovimientoClienteRepository archivoMovimientoClienteRepository;
	
	@Autowired
	private MovClienteXMovBancoRepository movClienteXMovBancoRepository;

	@Autowired
	private FacturaRepository facturaRepository;

	@Autowired
	private ReciboRepository reciboRepository;

	@Override
	public Page<ArchivoBanco> obtenerArchivosConciliacion(PageRequest paginaRequest, String filtro, Instant fechaInicio, Instant fechaFin) {
		if (ObjectUtils.isNotEmpty(filtro) && ObjectUtils.isNotEmpty(fechaInicio) && ObjectUtils.isNotEmpty(fechaFin)) {
			return archivoConciliacionRepository.findAll(FiltroArchivoBanco.contieneFechaMasTexto(filtro, fechaInicio, fechaFin), paginaRequest);			
		} else if (ObjectUtils.isNotEmpty(fechaInicio) && ObjectUtils.isNotEmpty(fechaFin)) {
			return archivoConciliacionRepository.findAll(FiltroArchivoBanco.contieneFecha(fechaInicio, fechaFin), paginaRequest);
		} else {
			return archivoConciliacionRepository.findAll(FiltroArchivoBanco.contieneTexto(filtro), paginaRequest);
		}
	}

	@Override
	public ArchivoBancoDTO obtenerArchivoConciliacionId(Long idArchivo) {
		return archivoConciliacionMapper.toDto(archivoConciliacionRepository.getReferenceById(idArchivo));
	}

	@Override
	public ArchivoBancoDTO saveArchivoConciliacion(ArchivoBancoDTO archivoConciliacionDTO,
			List<DetalleArchivoBancoDTO> detalleArchivo, Long idTipoBanco) {

		ArchivoBanco archivo = archivoConciliacionMapper.toEntity(archivoConciliacionDTO);
		archivo.setBanco(tipoBancoRepository.getReferenceById(idTipoBanco));
		archivo.setFechaCreacion(Instant.now());
		archivo.setIsActive(true);
		ArchivoBanco result = archivoConciliacionRepository.save(archivo);

		int exito = 0;
		int error = 0;
		int repetido = 0;

		for (DetalleArchivoBancoDTO detalleDTO : detalleArchivo) {
			ArchivoMovimientoCliente movCliente = null;
			MovClienteXMovBanco movClienteXMovBanco = null;
			
			try {
				DetalleArchivoBanco detalle = detalleArchivoConciliacionMapper.toEntity(detalleDTO);
				detalle.setArchivo(result);
				detalle.setFechaCreacion(Instant.now());
				detalle.setIsActive(true);
				
				movClienteXMovBanco = movClienteXMovBancoRepository.getByNumeroComprobante(detalle.getDocumento());
				if (ObjectUtils.isNotEmpty(movClienteXMovBanco) && ObjectUtils.isNotEmpty(movClienteXMovBanco.getDetalleArchivoBanco())) {
					detalle.setEstadoConciliacion(EstadoConciliacion.CDU);
					detalle = detalleArchivoConciliacionRepository.save(detalle);
				} else if (ObjectUtils.isNotEmpty(movClienteXMovBanco) && ObjectUtils.isEmpty(movClienteXMovBanco.getDetalleArchivoBanco())) {
					movCliente = movClienteXMovBanco.getArchivoMovimientoCliente();
					
					detalle.setEstadoConciliacion(validarEstadoConciliacion(detalle, movCliente));
					detalle = detalleArchivoConciliacionRepository.save(detalle);
					
					movCliente.setEstadoConciliacion(detalle.getEstadoConciliacion());
					archivoMovimientoClienteRepository.save(movCliente);

					movClienteXMovBanco.setDetalleArchivoBanco(detalle);
					movClienteXMovBancoRepository.save(movClienteXMovBanco);
					
					generarReciboConciliado(movClienteXMovBanco);
				} else {
					movCliente = archivoMovimientoClienteRepository.getByNumeroComprobante(detalle.getDocumento());
					
					if (ObjectUtils.isNotEmpty(movCliente)) {
						detalle.setEstadoConciliacion(validarEstadoConciliacion(detalle, movCliente));
						detalle = detalleArchivoConciliacionRepository.save(detalle);
						
						movCliente.setEstadoConciliacion(detalle.getEstadoConciliacion());
						archivoMovimientoClienteRepository.save(movCliente);
					} else {
						detalle.setEstadoConciliacion(EstadoConciliacion.NCO);
						detalle = detalleArchivoConciliacionRepository.save(detalle);
					}
					
					registrarMovClienteXMovBanco(detalle, movCliente);
				}
				
				exito++;
			} catch (Exception ex) {
				String message = "";
				DetalleErrorArchivoBanco detalle = new DetalleErrorArchivoBanco();
				detalle.setArchivo(result);
				detalle.setFecha(detalleDTO.getFecha());
				detalle.setDocumento(detalleDTO.getDocumento());
				detalle.setValor(detalleDTO.getValor());
				detalle.setReferencia(detalleDTO.getReferencia());
				detalle.setFechaCreacion(Instant.now());
				detalle.setIsActive(true);

				if (ObjectUtils.isEmpty(detalleDTO.getFecha()) || ObjectUtils.isEmpty(detalleDTO.getDocumento())
						|| ObjectUtils.isEmpty(detalleDTO.getValor())) {
					message = "REGISTRO INCOMPLETO O CON ERROR";
					error++;
				} else {
					message = "REGISTRO REPETIDO";
					repetido++;
				}

				detalle.setMensajeError(message);
				detalle.setCausaError(ex.getMessage());
				detalleErrorArchivoConciliacionRepository.save(detalle);
				continue;
			}
		}
		result.setRegistrosError(error);
		result.setRegistrosExito(exito);
		result.setRegistrosRepetido(repetido);
		archivo.setFechaFinCarga(Instant.now());
		archivo.setEstadoCarga(EstadoCarga.FIN);
		return archivoConciliacionMapper.toDto(archivoConciliacionRepository.save(result));
	}
	
	private EstadoConciliacion validarEstadoConciliacion(DetalleArchivoBanco detalle, ArchivoMovimientoCliente movimiento) {
		Instant fecha = detalle.getFecha();
		Double valor = detalle.getValor();
		if (ObjectUtils.isNotEmpty(movimiento)) {
			Boolean aprobado = movimiento.getAprobacion();
			Boolean mismaFecha = ObjectUtils.isNotEmpty(fecha) && fecha == movimiento.getFechaComprobante();
			Boolean mismoValor = ObjectUtils.isNotEmpty(valor) && valor == movimiento.getValorComprobante();
			return aprobado && mismaFecha && mismoValor ? EstadoConciliacion.CCO : EstadoConciliacion.NCO;
		} else {
			return EstadoConciliacion.NCO;
		}
	}
	
	private void registrarMovClienteXMovBanco(DetalleArchivoBanco detalle, ArchivoMovimientoCliente movimiento) {
		MovClienteXMovBanco mov = new MovClienteXMovBanco();
		mov.setDetalleArchivoBanco(detalle);
		mov.setNumeroComprobante(detalle.getDocumento());
		mov.setValorComprobante(detalle.getValor());
		mov.setFechaComprobante(detalle.getFecha());
		mov.setReferenciaComprobante(detalle.getReferencia());
		mov.setFechaCreacion(Instant.now());
		mov.setIsActive(true);
		
		if (ObjectUtils.isNotEmpty(movimiento)) {
			mov.setArchivoMovimientoCliente(movimiento);
			mov.setCliente(movimiento.getCliente());
			mov.setContrato(movimiento.getContrato());
		}
		
		mov = movClienteXMovBancoRepository.save(mov);
		
		generarReciboConciliado(mov);
	}

	private void generarReciboConciliado(MovClienteXMovBanco movimiento) {
		ArchivoMovimientoCliente movCliente = movimiento.getArchivoMovimientoCliente();
		DetalleArchivoBanco movBanco = movimiento.getDetalleArchivoBanco();
		Instant fechaActual = Instant.now();

		if (ObjectUtils.isNotEmpty(movCliente) && ObjectUtils.isNotEmpty(movBanco)) {
			if ((movCliente.getEstadoConciliacion() == EstadoConciliacion.CCO
					|| movCliente.getEstadoConciliacion() == EstadoConciliacion.CMA)
					&& (movBanco.getEstadoConciliacion() == EstadoConciliacion.CCO
							|| movBanco.getEstadoConciliacion() == EstadoConciliacion.CMA)) {
				Factura factura = facturaRepository.getByFacturaRecienteIdContrato(
						movCliente.getContrato().getNumContrato(), EstadoFactura.PENDIENTE);

				if (ObjectUtils.isNotEmpty(factura)) {
					Double nuevoSaldo = factura.getSaldo() - movBanco.getValor();

					factura.setSaldo(nuevoSaldo);
					facturaRepository.save(factura);

					Recibo recibo = new Recibo();
					recibo.setFactura(factura);
					recibo.setBanco(movBanco.getArchivo().getBanco());
					recibo.setFechaPago(fechaActual);
					recibo.setFormaPago(FormaPago.TRANSFERENCIA);
					recibo.setTipoPago(TipoPago.ABONO);
					recibo.setValor(movBanco.getValor());
					recibo.setSaldo(nuevoSaldo);
					recibo.setNumeroComprobante(movBanco.getDocumento().toString());
					recibo.setFechaComprobante(movBanco.getFecha());
					recibo.setFechaCreacion(fechaActual);
					recibo.setIsActive(true);
					reciboRepository.save(recibo);
				}
			}
		}
	}
}
