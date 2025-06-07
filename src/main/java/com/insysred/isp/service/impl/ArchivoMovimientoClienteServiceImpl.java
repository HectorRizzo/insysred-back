package com.insysred.isp.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.util.Base64;
import com.insysred.isp.dto.ArchivoMovimientoClienteContratoDetDTO;
import com.insysred.isp.dto.ArchivoMovimientoClienteDTO;
import com.insysred.isp.dto.ArchivoMovimientoClienteUltimosDTO;
import com.insysred.isp.dto.ConciliarArchivoMovimientoClienteDTO;
import com.insysred.isp.dto.ExistenciaMovClienteXMovBancoDTO;
import com.insysred.isp.entities.ArchivoMovimientoCliente;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Contrato;
import com.insysred.isp.entities.Credito;
import com.insysred.isp.entities.DetalleArchivoBanco;
import com.insysred.isp.entities.Factura;
import com.insysred.isp.entities.MovClienteXMovBanco;
import com.insysred.isp.entities.Recibo;
import com.insysred.isp.enums.EstadoConciliacion;
import com.insysred.isp.enums.EstadoCredito;
import com.insysred.isp.enums.EstadoFactura;
import com.insysred.isp.enums.FormaPago;
import com.insysred.isp.enums.TipoPago;
import com.insysred.isp.filtros.FiltroArchivoMovimientoCliente;
import com.insysred.isp.mapper.ArchivoMovimientoClienteMapper;
import com.insysred.isp.repository.ArchivoMovimientoClienteRepository;
import com.insysred.isp.repository.ClienteRepository;
import com.insysred.isp.repository.ContratoRepository;
import com.insysred.isp.repository.CreditoRepository;
import com.insysred.isp.repository.DetalleArchivoBancoRepository;
import com.insysred.isp.repository.FacturaRepository;
import com.insysred.isp.repository.MovClienteXMovBancoRepository;
import com.insysred.isp.repository.ReciboRepository;
import com.insysred.isp.repository.TipoBancoRepository;
import com.insysred.isp.service.declare.ArchivoMovimientoClienteService;

@Service
public class ArchivoMovimientoClienteServiceImpl implements ArchivoMovimientoClienteService {

	@Autowired
	private ArchivoMovimientoClienteRepository archivoMovimientoClienteRepository;

	@Autowired
	private ArchivoMovimientoClienteMapper archivoMovimientoClienteMapper;

	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TipoBancoRepository tipoBancoRepository;
	
	@Autowired
	private MovClienteXMovBancoRepository movClienteXMovBancoRepository;

	@Autowired
	private DetalleArchivoBancoRepository detalleArchivoBancoRepository;

	@Autowired
	private FacturaRepository facturaRepository;

	@Autowired
	private ReciboRepository reciboRepository;
	
	@Autowired
	private CreditoRepository creditoRepository;

	@Override
	public List<ArchivoMovimientoClienteContratoDetDTO> obtenerContratosCliente(Long idCliente) throws Exception {
		try {
			List<Contrato> contratos = contratoRepository.getByIdCliente(idCliente);
			List<ArchivoMovimientoClienteContratoDetDTO> list = new ArrayList<ArchivoMovimientoClienteContratoDetDTO>();

			for (Contrato contrato : contratos) {
				ArchivoMovimientoClienteContratoDetDTO det = new ArchivoMovimientoClienteContratoDetDTO();
				det.setIdContrato(contrato.getNumContrato());
				det.setDireccion(contrato.getUbicacion());
				det.setFechaContrato(Date.from(contrato.getFechaContrato()));
				det.setEstadoContrato(contrato.getEstadoContrato());
				list.add(det);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Ha ocurrido un error: " + e.getMessage());
		}
	}

	@Override
	public List<ArchivoMovimientoClienteUltimosDTO> obtenerArchivosMovimientoClienteUltimos(Long idCliente)
			throws Exception {
		try {
			Optional<Cliente> optCliente = clienteRepository.findById(idCliente);
			if (!optCliente.isPresent()) {
				throw new Exception("El cliente no existe.");
			}

			List<ArchivoMovimientoCliente> archivos = archivoMovimientoClienteRepository.getByIdCliente(idCliente, 3);
			List<ArchivoMovimientoClienteUltimosDTO> lsUltimos = new ArrayList<ArchivoMovimientoClienteUltimosDTO>();

			for (ArchivoMovimientoCliente archivo : archivos) {
				Path rutaArchivo = Paths.get(archivo.getRutaArchivo());

				if (!Files.exists(rutaArchivo)) {
					continue;
				}

				byte[] bytes = Files.readAllBytes(rutaArchivo);

				ArchivoMovimientoClienteUltimosDTO dto = new ArchivoMovimientoClienteUltimosDTO();
				dto.setIdContrato(archivo.getContrato().getNumContrato());
				dto.setFechaCreacion(archivo.getFechaCreacion());
				dto.setImagen("data:image/jpg;base64," + new String(Base64.encode(bytes), "UTF-8"));
				lsUltimos.add(dto);
			}

			return lsUltimos;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Ha ocurrido un error: " + e.getMessage());
		}
	}

	@Override
	public Page<ArchivoMovimientoCliente> obtenerArchivosMovimientoCliente(PageRequest paginaRequest, String filtro,
			Instant fechaInicio, Instant fechaFin) {
		if (ObjectUtils.isNotEmpty(filtro) && ObjectUtils.isNotEmpty(fechaInicio) && ObjectUtils.isNotEmpty(fechaFin)) {
			return archivoMovimientoClienteRepository.findAll(
					FiltroArchivoMovimientoCliente.contieneFechaMasTexto(filtro, fechaInicio, fechaFin), paginaRequest);
		} else if (ObjectUtils.isNotEmpty(fechaInicio) && ObjectUtils.isNotEmpty(fechaFin)) {
			return archivoMovimientoClienteRepository
					.findAll(FiltroArchivoMovimientoCliente.contieneFecha(fechaInicio, fechaFin), paginaRequest);
		} else {
			return archivoMovimientoClienteRepository.findAll(FiltroArchivoMovimientoCliente.contieneTexto(filtro),
					paginaRequest);
		}
	}

	@Override
	public ArchivoMovimientoClienteDTO obtenerArchivoMovimientoClienteId(Long idArchivo) {
		ArchivoMovimientoCliente archivo = archivoMovimientoClienteRepository.getReferenceById(idArchivo);
		return this.mapperFileArchivoMovimientoClienteDTO(archivo);
	}

	@Override
	public void uploadArchivoMovimientoCliente(Long idCliente, Long idContrato, MultipartFile archivo)
			throws Exception {

		Path absolutePath = Paths.get(File.listRoots()[0].getAbsolutePath(), "archivos");

		try {
			String nombreInterno = UUID.randomUUID().toString();
			String nombreArchivo = archivo.getOriginalFilename();
			String extension = FilenameUtils.getExtension(nombreArchivo);
			Path rutaArchivo = Paths.get(absolutePath.toString(), nombreInterno + "." + extension);

			Optional<Cliente> optCliente = clienteRepository.findById(idCliente);
			if (!optCliente.isPresent()) {
				throw new Exception("El cliente no existe.");
			}

			Optional<Contrato> optContrato = contratoRepository.findById(idContrato);
			if (!optContrato.isPresent()) {
				throw new Exception("El contrato seleccionado no es válido");
			}

			if (!Files.exists(absolutePath)) {
				Files.createDirectories(absolutePath);
			}

			Files.write(rutaArchivo, IOUtils.toByteArray(archivo.getInputStream()));

			ArchivoMovimientoCliente movimiento = new ArchivoMovimientoCliente();
			movimiento.setCliente(optCliente.get());
			movimiento.setContrato(optContrato.get());
			movimiento.setRutaArchivo(rutaArchivo.toString());
			movimiento.setNombreOriginal(nombreArchivo);
			movimiento.setNombreInterno(nombreInterno + "." + extension);
			movimiento.setExtension(extension);
			movimiento.setAprobacion(false);
			movimiento.setEstadoConciliacion(EstadoConciliacion.NCO);
			movimiento.setFechaCreacion(Instant.now());
			movimiento.setIsActive(true);
			archivoMovimientoClienteRepository.save(movimiento);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Ha ocurrido un error: " + e.getMessage());
		}
	}
	
	@Override
	public ExistenciaMovClienteXMovBancoDTO validarComprobanteMovClienteXMovBanco(Long numeroComprobante) {
		MovClienteXMovBanco movClienteXMovBanco = movClienteXMovBancoRepository.getByNumeroComprobanteExiste(numeroComprobante);
		ExistenciaMovClienteXMovBancoDTO existencia = null;
		ArchivoMovimientoCliente movCliente = null; 
		DetalleArchivoBanco movBanco = null;
		
		if (ObjectUtils.isNotEmpty(movClienteXMovBanco)) {
			movCliente = movClienteXMovBanco.getArchivoMovimientoCliente();
			movBanco = movClienteXMovBanco.getDetalleArchivoBanco();
			
			existencia = new ExistenciaMovClienteXMovBancoDTO();
			existencia.setIdMovClienteXMovBanco(movClienteXMovBanco.getId());
			
			if (ObjectUtils.isNotEmpty(movCliente)) {
				existencia.setIdMovCliente(movCliente.getId());
			}
			if (ObjectUtils.isNotEmpty(movBanco)) {
				existencia.setIdMovBanco(movBanco.getId());				
			}
		}
		return existencia;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public ArchivoMovimientoClienteDTO saveArchivoMovimientoCliente(ConciliarArchivoMovimientoClienteDTO validarArchivoMovimientoCliente) {
		MovClienteXMovBanco movClienteXMovBanco = null;
		DetalleArchivoBanco detalleArchivoBanco = null;
		
		try {
			Long idArchivo = validarArchivoMovimientoCliente.getIdArchivo();
			Long idConMovBanco = validarArchivoMovimientoCliente.getIdConMovBanco();
			Long idConMovCliente = validarArchivoMovimientoCliente.getIdConMovCliente();
			Long idConMovClienteXMovBanco = validarArchivoMovimientoCliente.getIdConMovClienteXMovBanco();
			
			ArchivoMovimientoCliente movimiento = archivoMovimientoClienteRepository.getReferenceById(validarArchivoMovimientoCliente.getIdArchivo());
			movimiento.setNumeroComprobante(validarArchivoMovimientoCliente.getNumeroComprobante());
			movimiento.setValorComprobante(validarArchivoMovimientoCliente.getValorComprobante());
			movimiento.setFechaComprobante(validarArchivoMovimientoCliente.getFechaComprobante());
			movimiento.setAprobacion(validarArchivoMovimientoCliente.getAprobacion());
			movimiento.setFechaModificacion(Instant.now());
			
			if (ObjectUtils.isNotEmpty(validarArchivoMovimientoCliente.getIdBanco())) {
				movimiento.setBanco(tipoBancoRepository.getReferenceById(validarArchivoMovimientoCliente.getIdBanco()));
			}
			
			if (validarArchivoMovimientoCliente.getAnular()) {
				ArchivoMovimientoCliente movCliente = archivoMovimientoClienteRepository.getReferenceById(idConMovCliente);
				movClienteXMovBanco = movClienteXMovBancoRepository.getReferenceById(idConMovClienteXMovBanco);
				detalleArchivoBanco = movClienteXMovBanco.getDetalleArchivoBanco();
				
				movimiento.setIsActive(true);
				movimiento.setEstadoConciliacion(validarEstadoConciliacion(detalleArchivoBanco, movimiento));
				movimiento = archivoMovimientoClienteRepository.save(movimiento);
				
				movCliente.setIsActive(false);
				archivoMovimientoClienteRepository.save(movCliente);
				
				movClienteXMovBanco.setArchivoMovimientoCliente(movimiento);
				movClienteXMovBanco.setCliente(movimiento.getCliente());
				movClienteXMovBancoRepository.save(movClienteXMovBanco);
				
				generarReciboConciliado(movClienteXMovBanco);
				
				return this.mapperFileArchivoMovimientoClienteDTO(movimiento);
			}
			
			if (!movimiento.getAprobacion() || ObjectUtils.isEmpty(movimiento.getNumeroComprobante()) || movimiento.getEstadoConciliacion() == EstadoConciliacion.CDU) {
				movimiento = archivoMovimientoClienteRepository.save(movimiento);
				return this.mapperFileArchivoMovimientoClienteDTO(movimiento);
			}
			
			if (ObjectUtils.isNotEmpty(idConMovCliente) && ObjectUtils.isNotEmpty(idConMovBanco)) {
				if (idConMovCliente == idArchivo) {
					detalleArchivoBanco = detalleArchivoBancoRepository.getReferenceById(idConMovBanco);
					
					movimiento.setEstadoConciliacion(validarEstadoConciliacion(detalleArchivoBanco, movimiento));
					detalleArchivoBanco.setEstadoConciliacion(movimiento.getEstadoConciliacion());
					detalleArchivoBancoRepository.save(detalleArchivoBanco);
					
					movClienteXMovBanco = movClienteXMovBancoRepository.getReferenceById(idConMovClienteXMovBanco);
					movClienteXMovBanco.setNumeroComprobante(detalleArchivoBanco.getDocumento());
					movClienteXMovBanco.setValorComprobante(detalleArchivoBanco.getValor());
					movClienteXMovBanco.setFechaComprobante(detalleArchivoBanco.getFecha());
					movClienteXMovBanco.setReferenciaComprobante(detalleArchivoBanco.getReferencia());
					movClienteXMovBanco.setCliente(movimiento.getCliente());
					movClienteXMovBancoRepository.save(movClienteXMovBanco);
					
					generarReciboConciliado(movClienteXMovBanco);
				} else {
					movimiento.setEstadoConciliacion(EstadoConciliacion.CDU);
				}
				
				movimiento = archivoMovimientoClienteRepository.save(movimiento);
			} else if (ObjectUtils.isNotEmpty(idConMovCliente) && ObjectUtils.isEmpty(idConMovBanco)) {
				if (idConMovCliente == idArchivo) {
					movimiento.setEstadoConciliacion(validarEstadoConciliacion(detalleArchivoBanco, movimiento));
				} else {
					movimiento.setEstadoConciliacion(EstadoConciliacion.CDU);
				}
				
				movimiento = archivoMovimientoClienteRepository.save(movimiento);
			} else if (ObjectUtils.isEmpty(idConMovCliente) && ObjectUtils.isNotEmpty(idConMovBanco)) {
				movClienteXMovBanco = movClienteXMovBancoRepository.getReferenceById(idConMovClienteXMovBanco);
				detalleArchivoBanco = movClienteXMovBanco.getDetalleArchivoBanco();
				
				movimiento.setEstadoConciliacion(validarEstadoConciliacion(detalleArchivoBanco, movimiento));
				movimiento = archivoMovimientoClienteRepository.save(movimiento);
				
				detalleArchivoBanco.setEstadoConciliacion(movimiento.getEstadoConciliacion());
				detalleArchivoBancoRepository.save(detalleArchivoBanco);
				
				movClienteXMovBanco.setArchivoMovimientoCliente(movimiento);
				movClienteXMovBanco.setCliente(movimiento.getCliente());
				movClienteXMovBancoRepository.save(movClienteXMovBanco);
				
				generarReciboConciliado(movClienteXMovBanco);
			} else {
				ExistenciaMovClienteXMovBancoDTO existencia  = validarComprobanteMovClienteXMovBanco(movimiento.getNumeroComprobante());
				
				if (ObjectUtils.isNotEmpty(existencia)) {
					movClienteXMovBanco = movClienteXMovBancoRepository.getReferenceById(existencia.getIdMovClienteXMovBanco());
					detalleArchivoBanco = movClienteXMovBanco.getDetalleArchivoBanco();
				}
				
				if (ObjectUtils.isEmpty(detalleArchivoBanco)) {
					detalleArchivoBanco = detalleArchivoBancoRepository.getByDocumento(movimiento.getNumeroComprobante());
				}
				
				movimiento.setEstadoConciliacion(validarEstadoConciliacion(detalleArchivoBanco, movimiento));
				movimiento = archivoMovimientoClienteRepository.save(movimiento);
				
				if (ObjectUtils.isNotEmpty(existencia)) {
					idConMovBanco = existencia.getIdMovBanco();
					idConMovCliente = existencia.getIdMovCliente();
					
					if (ObjectUtils.isNotEmpty(idConMovCliente) && idConMovCliente != movimiento.getId() && ObjectUtils.isEmpty(idConMovBanco) && ObjectUtils.isNotEmpty(detalleArchivoBanco)) {
						detalleArchivoBanco.setEstadoConciliacion(movimiento.getEstadoConciliacion());
						detalleArchivoBancoRepository.save(detalleArchivoBanco);
						
						movClienteXMovBanco.setDetalleArchivoBanco(detalleArchivoBanco);
						movClienteXMovBanco.setCliente(movimiento.getCliente());
						movClienteXMovBancoRepository.save(movClienteXMovBanco);
						
						generarReciboConciliado(movClienteXMovBanco);
					} else if (ObjectUtils.isNotEmpty(idConMovBanco) && ObjectUtils.isEmpty(idConMovCliente)) {
						detalleArchivoBanco.setEstadoConciliacion(movimiento.getEstadoConciliacion());
						detalleArchivoBancoRepository.save(detalleArchivoBanco);
						
						movClienteXMovBanco.setArchivoMovimientoCliente(movimiento);
						movClienteXMovBanco.setCliente(movimiento.getCliente());
						movClienteXMovBancoRepository.save(movClienteXMovBanco);
						
						generarReciboConciliado(movClienteXMovBanco);
					}
				} else {
					if (ObjectUtils.isNotEmpty(detalleArchivoBanco)) {
						detalleArchivoBanco.setEstadoConciliacion(movimiento.getEstadoConciliacion());
						detalleArchivoBancoRepository.save(detalleArchivoBanco);
					}
					
					registrarMovClienteXMovBanco(detalleArchivoBanco, movimiento);
				}
			}
			
			return this.mapperFileArchivoMovimientoClienteDTO(movimiento);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private ArchivoMovimientoClienteDTO mapperFileArchivoMovimientoClienteDTO(ArchivoMovimientoCliente archivo) {

		ArchivoMovimientoClienteDTO dto = archivoMovimientoClienteMapper.toDto(archivo);

		try {
			Path rutaArchivo = Paths.get(archivo.getRutaArchivo());
			if (Files.exists(rutaArchivo)) {
				byte[] bytes = Files.readAllBytes(rutaArchivo);
				dto.setImagen("data:image/jpg;base64," + new String(Base64.encode(bytes), "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}
	
	private EstadoConciliacion validarEstadoConciliacion(DetalleArchivoBanco detalle, ArchivoMovimientoCliente movimiento) {
		Instant fecha = movimiento.getFechaComprobante();
		Double valor = movimiento.getValorComprobante();
		
		if (ObjectUtils.isNotEmpty(detalle) && detalle.getIsActive()) {
			Boolean mismaFecha = ObjectUtils.isNotEmpty(fecha) && fecha.compareTo(detalle.getFecha()) == 0;
			Boolean mismoValor = ObjectUtils.isNotEmpty(valor) && valor.compareTo(detalle.getValor()) == 0;
			return mismaFecha && mismoValor ? EstadoConciliacion.CCO : EstadoConciliacion.NCO;
		} else {
			return EstadoConciliacion.NCO;
		}
	}
	
	private void registrarMovClienteXMovBanco(DetalleArchivoBanco detalle, ArchivoMovimientoCliente movimiento) {
		MovClienteXMovBanco mov = new MovClienteXMovBanco();
		mov.setArchivoMovimientoCliente(movimiento);
		mov.setCliente(movimiento.getCliente());
		mov.setContrato(movimiento.getContrato());
		mov.setFechaCreacion(Instant.now());
		mov.setIsActive(true);
		
		if (ObjectUtils.isNotEmpty(detalle)) {
			mov.setDetalleArchivoBanco(detalle);
			mov.setNumeroComprobante(detalle.getDocumento());
			mov.setValorComprobante(detalle.getValor());
			mov.setFechaComprobante(detalle.getFecha());
			mov.setReferenciaComprobante(detalle.getReferencia());
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
					Double calculoSaldo = factura.getSaldo() - movBanco.getValor();
					Double nuevoSaldo = calculoSaldo < 0 ? 0 : calculoSaldo;

					Recibo recibo = new Recibo();
					recibo.setFactura(factura);
					recibo.setBanco(movBanco.getArchivo().getBanco());
					recibo.setFechaPago(fechaActual);
					recibo.setFormaPago(FormaPago.TRANSFERENCIA);
					recibo.setTipoPago(nuevoSaldo == 0 ? TipoPago.TOTAL : TipoPago.ABONO);
					recibo.setValor(nuevoSaldo == 0 ? factura.getSaldo() : movBanco.getValor());
					recibo.setSaldo(nuevoSaldo);
					recibo.setNumeroComprobante(movBanco.getDocumento().toString());
					recibo.setFechaComprobante(movBanco.getFecha());
					recibo.setFechaCreacion(fechaActual);
					recibo.setEsConciliacion(true);
					recibo.setIsActive(true);
					reciboRepository.save(recibo);

					factura.setSaldo(nuevoSaldo);
					factura.setEstado(nuevoSaldo == 0 ? EstadoFactura.PAGADA : EstadoFactura.PENDIENTE);
					facturaRepository.save(factura);
					
					if (calculoSaldo < 0) {
						generarCredito(factura.getContrato(), factura.getCliente(), calculoSaldo);
					}
				} else {
					generarCredito(movCliente.getContrato(), movCliente.getCliente(), movBanco.getValor());
				}
			}
		}
	}
	
	private void generarCredito(Contrato contrato, Cliente cliente, Double valor) {
		Credito credito = new Credito();
		credito.setContrato(contrato);
		credito.setCliente(cliente);
		credito.setFechaCredito(Instant.now());
		credito.setValorCredito(Math.abs(valor));
		credito.setSaldo(Math.abs(valor));
		credito.setEstadoCredito(EstadoCredito.VIGENTE);
		credito.setEsConciliacion(true);
		credito.setIsActive(true);
		credito.setFechaCreacion(Instant.now());
		creditoRepository.save(credito);		
	}
}
