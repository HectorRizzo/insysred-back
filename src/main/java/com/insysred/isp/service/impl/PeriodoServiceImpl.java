package com.insysred.isp.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.insysred.isp.dto.PeriodoDTO;
import com.insysred.isp.dto.ProcesarPeriodoResultadoDto;
import com.insysred.isp.entities.Contrato;
import com.insysred.isp.entities.Credito;
import com.insysred.isp.entities.CreditoXFactura;
import com.insysred.isp.entities.DetalleFactura;
import com.insysred.isp.entities.Factura;
import com.insysred.isp.entities.Periodo;
import com.insysred.isp.entities.Planes;
import com.insysred.isp.entities.Rubros;
import com.insysred.isp.entities.RubrosXContrato;
import com.insysred.isp.enums.EstadoCredito;
import com.insysred.isp.enums.EstadoFactura;
import com.insysred.isp.enums.EstadoPeriodo;
import com.insysred.isp.enums.EstadoRubroXContrato;
import com.insysred.isp.mapper.PeriodoMapper;
import com.insysred.isp.repository.ContratoRepository;
import com.insysred.isp.repository.CreditoRepository;
import com.insysred.isp.repository.CreditoXFacturaRepository;
import com.insysred.isp.repository.DetalleFacturaRepository;
import com.insysred.isp.repository.FacturaRepository;
import com.insysred.isp.repository.PeriodoRepository;
import com.insysred.isp.repository.RubrosXContratoRepository;
import com.insysred.isp.service.declare.ParametrosGeneralesService;
import com.insysred.isp.service.declare.PeriodoService;

@Service
public class PeriodoServiceImpl implements PeriodoService {

	@Autowired
	private PeriodoRepository periodoRepository;

	@Autowired
	private PeriodoMapper periodoMapper;

	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	private RubrosXContratoRepository rubrosXContratoRepository;

	@Autowired
	private FacturaRepository facturaRepository;

	@Autowired
	private DetalleFacturaRepository detalleFacturaRepository;

	@Autowired
	private ParametrosGeneralesService parametrosGeneralesService;
	
	@Autowired
	private CreditoRepository creditoRepository;
	
	@Autowired
	private CreditoXFacturaRepository creditoXFacturaRepository;

	@Override
	public List<PeriodoDTO> obtenerPeriodosAnioActual() {
		Integer anioActual = Year.now().getValue();
		Integer anioAnterior = anioActual - 1;
		List<PeriodoDTO> actual = periodoMapper
				.toDto(periodoRepository.getByFechaPeriodo(anioActual, EstadoPeriodo.NO_PROCESADO));
		List<PeriodoDTO> anterior = periodoMapper
				.toDto(periodoRepository.getByFechaPeriodo(anioAnterior, EstadoPeriodo.NO_PROCESADO));
		if (ObjectUtils.isNotEmpty(anterior)) {
			actual.addFirst(anterior.get(anterior.size() - 1));
		}
		return actual;
	}

	@Override
	public List<PeriodoDTO> obtenerPeriodosAnioActualIndividual() {
		LocalDate localDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDateMinus = localDate.minusMonths(3);
		LocalDate localDatePlus = localDate.plusMonths(2);

		Instant intervaloMenor = localDateMinus.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Instant intervaloMayor = localDatePlus.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

		return periodoMapper.toDto(periodoRepository.getByFechaPeriodoIndividual(intervaloMenor, intervaloMayor));
	}

	@Override
	public ProcesarPeriodoResultadoDto procesarFacturas(Long idPeriodo, Long[] idContratos) throws Exception {
		ProcesarPeriodoResultadoDto resumen;
		Optional<Periodo> optPeriodo = null;
		Periodo periodo = null;
		Long diasPreCorte = parametrosGeneralesService.obtenerParametroValorNumber("FACTURAS_DIAS_PRE_CORTE");
		Long diasVencimiento = parametrosGeneralesService.obtenerParametroValorNumber("FACTURAS_DIAS_VENCIMIENTO");
		Long porcentajeIva = parametrosGeneralesService.obtenerParametroValorNumber("FACTURAS_PORCENTAJE_IVA");

		if (ObjectUtils.isEmpty(idPeriodo)) {
			throw new Exception("Los campos idPeriodo es obligatorio.");
		}

		optPeriodo = periodoRepository.findById(idPeriodo);

		if (!optPeriodo.isPresent()) {
			throw new Exception("El periodo no existe.");
		} else {
			periodo = optPeriodo.get();
		}

		if (ObjectUtils.isNotEmpty(idContratos)) {
			resumen = this.procesarFacturaByIdContrato(periodo, idContratos, diasPreCorte, diasVencimiento, porcentajeIva);
		} else {
			resumen = this.procesarFacturaMasivo(periodo, diasPreCorte, diasVencimiento, porcentajeIva);
		}

		return resumen;
	}

	private ProcesarPeriodoResultadoDto procesarFacturaByIdContrato(Periodo periodo, Long[] idContratos,
			Long diasPreCorte, Long diasVencimiento, Long porcentajeIva) throws Exception {
		ProcesarPeriodoResultadoDto resumen = new ProcesarPeriodoResultadoDto();
		Instant fechaActual = Instant.now();
		Double totalValorFacturas = 0.0;
		Integer conteoFacturas = 0;

		for (Long idContrato : idContratos) {
			try {
				Optional<Contrato> optContrato = contratoRepository.findById(idContrato);
				Contrato contrato = null;
				Instant fechaPeriodoHasta = periodo.getFechaHasta();
				Instant fechaContrato = null;

				if (!optContrato.isPresent()) {
					throw new Exception("El contrato seleccionado no existe.");
				} else {
					contrato = optContrato.get();
				}

				fechaContrato = contrato.getFechaContrato();
				if (fechaPeriodoHasta.compareTo(fechaContrato) < 0) {
					throw new Exception("El contrato no aplica para el periodo seleccionado.");
				}

				Factura factura = generarFactura(periodo, contrato, fechaActual, diasPreCorte, diasVencimiento, porcentajeIva);
				totalValorFacturas += factura.getTotal();
				conteoFacturas++;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}

		resumen.setTotalValorFacturas(totalValorFacturas);
		resumen.setConteoFacturas(conteoFacturas);
		return resumen;
	}

	private ProcesarPeriodoResultadoDto procesarFacturaMasivo(Periodo periodo, Long diasPreCorte, Long diasVencimiento, Long porcentajeIva)
			throws Exception {
		List<Contrato> contratos = null;
		ProcesarPeriodoResultadoDto resumen = new ProcesarPeriodoResultadoDto();

		Instant fechaActual = Instant.now();
		Double totalValorFacturas = 0.0;
		Integer conteoFacturas = 0;

		if (periodo.getEstadoPeriodo().equals(EstadoPeriodo.PROCESADO)) {
			throw new Exception("El proceso de factura para el periodo seleccionado ya fue procesado.");
		}

		if (fechaActual.compareTo(periodo.getFechaDesde()) < 0) {
			throw new Exception("El periodo seleccionado aún no ha empezado.");
		}

		contratos = contratoRepository.getByEstadoContratoValido();
		for (Contrato contrato : contratos) {
			try {
				Factura factura = generarFactura(periodo, contrato, fechaActual, diasPreCorte, diasVencimiento, porcentajeIva);
				totalValorFacturas += factura.getTotal();
				conteoFacturas++;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}

		if (conteoFacturas > 0) {
			periodo.setEstadoPeriodo(EstadoPeriodo.PROCESADO);
			periodoRepository.save(periodo);
		}

		resumen.setTotalValorFacturas(totalValorFacturas);
		resumen.setConteoFacturas(conteoFacturas);
		return resumen;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	private Factura generarFactura(Periodo periodo, Contrato contrato, Instant fechaActual, Long diasPreCorte,
			Long diasVencimiento, Long porcentajeIva) throws Exception {
		Factura factura = facturaRepository.getByIdContratoPeriodo(contrato.getNumContrato(), periodo.getId());
		Instant fechaPeriodoHasta = periodo.getFechaHasta();
		Double totalFactura, totalIva, subTotalFactura = 0.0;

		if (ObjectUtils.isNotEmpty(factura)) {
			throw new Exception("El contrato ya tiene una factura generada para el periodo seleccionado.");
		}
		
		diasPreCorte = ObjectUtils.isEmpty(diasPreCorte) || diasPreCorte == 0 ? 5 : diasPreCorte; 
		diasVencimiento = ObjectUtils.isEmpty(diasVencimiento) || diasVencimiento == 0 ? 15 : diasVencimiento;
		porcentajeIva = ObjectUtils.isEmpty(porcentajeIva) || porcentajeIva == 0 ? 12 : porcentajeIva;

		factura = new Factura();
		factura.setCliente(contrato.getCliente());
		factura.setContrato(contrato);
		factura.setEstado(EstadoFactura.PENDIENTE);
		factura.setPeriodo(periodo);
		factura.setFechaEmision(fechaActual);
		factura.setFechaPreCorte(fechaPeriodoHasta.plus(diasPreCorte, ChronoUnit.DAYS));
		factura.setFechaVencimiento(fechaPeriodoHasta.plus(diasVencimiento, ChronoUnit.DAYS));
		factura.setFechaCreacion(fechaActual);
		factura.setIsActive(true);
		factura = facturaRepository.save(factura);

		DetalleFactura detallePlan = generarDetalleFacturaByIdPlan(periodo, contrato, factura, fechaActual);
		subTotalFactura += detallePlan.getValor();

		List<RubrosXContrato> rubrosXContratos = rubrosXContratoRepository.getByIdContratoEstado(contrato.getNumContrato(), EstadoRubroXContrato.PENDIENTE);
		for (RubrosXContrato rubroXContrato : rubrosXContratos) {
			DetalleFactura detalleRubro = generarDetalleFacturaByIdRubro(rubroXContrato, factura, fechaActual);
			subTotalFactura += detalleRubro.getValor();
		}

		totalIva = subTotalFactura * porcentajeIva / 100;
		totalFactura = subTotalFactura + totalIva;

		factura.setValor(subTotalFactura);
		factura.setIva(totalIva);
		factura.setTotal(totalFactura);
		factura.setSaldo(totalFactura);
		
		// CREDITOS
		Credito credito = creditoRepository.getByContratoClienteEstado(contrato.getNumContrato(), contrato.getCliente().getId(), EstadoCredito.VIGENTE);
		if (ObjectUtils.isNotEmpty(credito)) {
			Double calculoSaldoFactura = factura.getSaldo() - credito.getSaldo();
			Boolean saldoFacturaNegativo = calculoSaldoFactura < 0.0;
			
			CreditoXFactura creditoFactura = new CreditoXFactura();
			creditoFactura.setCredito(credito);
			creditoFactura.setFactura(factura);
			creditoFactura.setValorAplicado(saldoFacturaNegativo ? factura.getSaldo() : credito.getSaldo());
			creditoFactura.setFechaCreacion(Instant.now());
			creditoFactura.setIsActive(true);
			creditoXFacturaRepository.save(creditoFactura);
			
			if (saldoFacturaNegativo) {
				credito.setSaldo(Math.abs(calculoSaldoFactura));
				credito.setEstadoCredito(EstadoCredito.VIGENTE);
				credito.setFechaModificacion(Instant.now());
				creditoRepository.save(credito);
				
				factura.setSaldo(0.0);
				factura.setEstado(EstadoFactura.PAGADA);
			} else {
				credito.setSaldo(0.0);
				credito.setEstadoCredito(EstadoCredito.APLICADO);
				credito.setFechaModificacion(Instant.now());
				creditoRepository.save(credito);
				
				factura.setSaldo(calculoSaldoFactura);
				factura.setEstado(calculoSaldoFactura == 0 ? EstadoFactura.PAGADA : EstadoFactura.PENDIENTE);
			}
		}
		
		return facturaRepository.save(factura);
	}

	private DetalleFactura generarDetalleFacturaByIdPlan(Periodo periodo, Contrato contrato, Factura factura,
			Instant fechaActual) {
		Planes plan = contrato.getPlan();

		DetalleFactura detalle = new DetalleFactura();
		detalle.setFactura(factura);
		detalle.setPlan(plan);
		detalle.setRubro(null);
		detalle.setCantidad(1);
		detalle.setValor(plan.getPrice());
		detalle.setFechaCreacion(fechaActual);
		detalle.setIsActive(true);

//		Instant fechaContrato = contrato.getFechaContrato();
//		Instant fechaFinContrato = contrato.getFechaFin();
//		Instant fechaPeriodoDesde = periodo.getFechaDesde();
//		Instant fechaPeriodoHasta = periodo.getFechaHasta();
//		if (fechaContrato.compareTo(fechaPeriodoDesde) > 0 && fechaContrato.compareTo(fechaPeriodoHasta) < 0) {
//			LocalDate fechaContratoLocalDate = fechaContrato.atZone(ZoneId.systemDefault()).toLocalDate();
//			Integer diasContrato = fechaContratoLocalDate.getDayOfMonth();
//			Integer numeroDias = (int) Duration.between(fechaPeriodoDesde, fechaPeriodoHasta).toDays() + 1;
//			Integer diferenciaDias = numeroDias - diasContrato + 1;
//			Double prorrateo = (plan.getPrice() * diferenciaDias) / numeroDias;
//			detalle.setValor(prorrateo);
//		} else if (ObjectUtils.isNotEmpty(fechaFinContrato) && fechaFinContrato.compareTo(fechaPeriodoDesde) > 0
//				&& fechaFinContrato.compareTo(fechaPeriodoHasta) < 0) {
//			LocalDate fechaFinContratoLocalDate = fechaFinContrato.atZone(ZoneId.systemDefault()).toLocalDate();
//			Integer diasContrato = fechaFinContratoLocalDate.getDayOfMonth() + 1;
//			Integer numeroDias = (int) Duration.between(fechaPeriodoDesde, fechaPeriodoHasta).toDays() + 1;
//			Double prorrateo = (plan.getPrice() * diasContrato) / numeroDias;
//			detalle.setValor(prorrateo);
//		}

		return detalleFacturaRepository.save(detalle);
	}

	private DetalleFactura generarDetalleFacturaByIdRubro(RubrosXContrato rubroXContrato, Factura factura,
			Instant fechaActual) {
		Rubros rubro = rubroXContrato.getRubro();
		Double total = rubro.getValor() * rubroXContrato.getCantidad();

		rubroXContrato.setFactura(factura);
		rubroXContrato.setEstado(EstadoRubroXContrato.ASIGNADO);
		rubroXContrato.setFechaAsignacion(fechaActual);
		rubroXContrato.setFechaModificacion(fechaActual);
		rubrosXContratoRepository.save(rubroXContrato);

		DetalleFactura detalleRubro = new DetalleFactura();
		detalleRubro.setFactura(factura);
		detalleRubro.setPlan(null);
		detalleRubro.setRubro(rubro);
		detalleRubro.setCantidad(rubroXContrato.getCantidad());
		detalleRubro.setValor(total);
		detalleRubro.setFechaCreacion(fechaActual);
		detalleRubro.setIsActive(true);
		return detalleFacturaRepository.save(detalleRubro);
	}

}
