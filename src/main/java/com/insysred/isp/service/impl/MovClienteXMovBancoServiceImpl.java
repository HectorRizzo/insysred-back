package com.insysred.isp.service.impl;

import java.time.Instant;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.insysred.isp.entities.ArchivoMovimientoCliente;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.ConciliacionManualMovBanco;
import com.insysred.isp.entities.ConciliacionManualMovCliente;
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
import com.insysred.isp.filtros.FiltroMovClienteXMovBanco;
import com.insysred.isp.mapper.MovClienteXMovBancoMapper;
import com.insysred.isp.repository.ArchivoMovimientoClienteRepository;
import com.insysred.isp.repository.ConciliacionManualMovBancoRepository;
import com.insysred.isp.repository.ConciliacionManualMovClienteRepository;
import com.insysred.isp.repository.CreditoRepository;
import com.insysred.isp.repository.DetalleArchivoBancoRepository;
import com.insysred.isp.repository.FacturaRepository;
import com.insysred.isp.repository.MovClienteXMovBancoRepository;
import com.insysred.isp.repository.ReciboRepository;
import com.insysred.isp.service.declare.MovClienteXMovBancoService;

@Service
public class MovClienteXMovBancoServiceImpl implements MovClienteXMovBancoService {

	@Autowired
	private MovClienteXMovBancoRepository movClienteXMovBancoRepository;

	@Autowired
	private MovClienteXMovBancoMapper movClienteXMovBancoMapper;

	@Autowired
	private ArchivoMovimientoClienteRepository archivoMovimientoClienteRepository;

	@Autowired
	private DetalleArchivoBancoRepository detalleArchivoBancoRepository;

	@Autowired
	private FacturaRepository facturaRepository;

	@Autowired
	private ReciboRepository reciboRepository;
	
	@Autowired
	private ConciliacionManualMovBancoRepository conciliacionManualMovBancoRepository;
	
	@Autowired
	private ConciliacionManualMovClienteRepository conciliacionManualMovClienteRepository;
	
	@Autowired
	private CreditoRepository creditoRepository;

	@Override
	public Page<MovClienteXMovBanco> obtenerConciliacion(PageRequest paginaRequest, String filtroCliente,
			String filtroComprobante, Long filtroBanco, Instant fechaInicio, Instant fechaFin, String estado) {
		return movClienteXMovBancoRepository.findAll(FiltroMovClienteXMovBanco.contieneFiltro(filtroCliente,
				filtroComprobante, filtroBanco, fechaInicio, fechaFin, estado), paginaRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void conciliacionManual(Long idMovClienteXMovBanco) {
		MovClienteXMovBanco movimiento = movClienteXMovBancoRepository.getReferenceById(idMovClienteXMovBanco);

		if (ObjectUtils.isNotEmpty(movimiento) && ObjectUtils.isNotEmpty(movimiento.getArchivoMovimientoCliente())
				&& ObjectUtils.isNotEmpty(movimiento.getDetalleArchivoBanco())) {
			ArchivoMovimientoCliente movCliente = movimiento.getArchivoMovimientoCliente();
			DetalleArchivoBanco movBanco = movimiento.getDetalleArchivoBanco();

			if (movCliente.getEstadoConciliacion() == EstadoConciliacion.NCO
					&& movBanco.getEstadoConciliacion() == EstadoConciliacion.NCO) {
				
				ConciliacionManualMovBanco manualMovBanco = new ConciliacionManualMovBanco();
				manualMovBanco.setDetalleArchivoBanco(movBanco);
				manualMovBanco.setEstadoConciliacionAnt(movBanco.getEstadoConciliacion());
				manualMovBanco.setFechaCreacion(Instant.now());
				manualMovBanco.setIsActive(true);
				conciliacionManualMovBancoRepository.save(manualMovBanco);
				
				ConciliacionManualMovCliente manualMovCliente = new ConciliacionManualMovCliente();
				manualMovCliente.setArchivoMovimientoCliente(movCliente);
				manualMovCliente.setEstadoConciliacionAnt(movBanco.getEstadoConciliacion());
				manualMovCliente.setFechaCreacion(Instant.now());
				manualMovCliente.setIsActive(true);
				conciliacionManualMovClienteRepository.save(manualMovCliente);
				
				movCliente.setEstadoConciliacion(EstadoConciliacion.CMA);
				archivoMovimientoClienteRepository.save(movCliente);

				movBanco.setEstadoConciliacion(EstadoConciliacion.CMA);
				detalleArchivoBancoRepository.save(movBanco);

				generarReciboConciliado(movimiento);
			}
		}
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
