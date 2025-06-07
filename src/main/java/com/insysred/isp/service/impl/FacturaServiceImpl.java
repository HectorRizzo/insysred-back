package com.insysred.isp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.AnularFacturaDto;
import com.insysred.isp.dto.AplicarDescuentoFacturaDto;
import com.insysred.isp.dto.CambioEstadoFacturaDto;
import com.insysred.isp.dto.FacturaDTO;
import com.insysred.isp.dto.ReporteReciboDTO;
import com.insysred.isp.dto.ReporteReciboDetalleDTO;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Contrato;
import com.insysred.isp.entities.DescuentoFactura;
import com.insysred.isp.entities.DetalleFactura;
import com.insysred.isp.entities.Empresa;
import com.insysred.isp.entities.Factura;
import com.insysred.isp.entities.FacturaPromesaPago;
import com.insysred.isp.entities.Recibo;
import com.insysred.isp.entities.Sucursales;
import com.insysred.isp.enums.EstadoFactura;
import com.insysred.isp.enums.FormaPago;
import com.insysred.isp.enums.TipoPago;
import com.insysred.isp.filtros.FiltroFactura;
import com.insysred.isp.mapper.FacturaMapper;
import com.insysred.isp.repository.DescuentoFacturaRepository;
import com.insysred.isp.repository.FacturaPromesaPagoRepository;
import com.insysred.isp.repository.FacturaRepository;
import com.insysred.isp.repository.ReciboRepository;
import com.insysred.isp.repository.RubrosXContratoRepository;
import com.insysred.isp.repository.TipoBancoRepository;
import com.insysred.isp.service.declare.FacturaService;
import com.insysred.isp.service.declare.JasperReportService;

@Service
public class FacturaServiceImpl implements FacturaService {

	@Autowired
	private FacturaRepository facturaRepository;

	@Autowired
	private FacturaMapper facturaMapper;

	@Autowired
	private FacturaPromesaPagoRepository facturaPromesaPagoRepository;

	@Autowired
	private ReciboRepository reciboRepository;

	@Autowired
	private RubrosXContratoRepository rubrosXContratoRepository;

	@Autowired
	private DescuentoFacturaRepository descuentoFacturaRepository;

	@Autowired
	private JasperReportService jasperReportService;

	@Autowired
	private TipoBancoRepository tipoBancoRepository;

	@Override
	public List<FacturaDTO> listaFactura() {
		return facturaMapper.toDto(facturaRepository.findAll());
	}

	@Override
	public Page<Factura> obtenerFacturas(PageRequest paginaRequest, String filtro, Instant fechaInicio,
			Instant fechaFin, String estado) {
		if (ObjectUtils.isNotEmpty(filtro) && ObjectUtils.isNotEmpty(fechaInicio) && ObjectUtils.isNotEmpty(fechaFin)) {
			return facturaRepository.findAll(FiltroFactura.contieneFechaMasTexto(filtro, fechaInicio, fechaFin),
					paginaRequest);
		} else if (ObjectUtils.isNotEmpty(fechaInicio) && ObjectUtils.isNotEmpty(fechaFin)) {
			return facturaRepository.findAll(FiltroFactura.contieneFecha(fechaInicio, fechaFin), paginaRequest);
		} else {
			return facturaRepository.findAll(FiltroFactura.contieneTexto(filtro, estado), paginaRequest);
		}
	}

	@Override
	public FacturaDTO obtenerFacturaId(Long idFactura) {
		return facturaMapper.toDto(facturaRepository.getReferenceById(idFactura));
	}

	@Override
	public FacturaDTO cmbEstadoFactura(CambioEstadoFacturaDto cambioEstadoFacturaDto) {
		Instant fechaActual = Instant.now();
		try {
			Factura factura = facturaRepository.getReferenceById(cambioEstadoFacturaDto.getIdFactura());
			factura.setEstado(cambioEstadoFacturaDto.getEstado());
			factura.setSaldo(cambioEstadoFacturaDto.getSaldo());
			factura.setFechaModificacion(fechaActual);

			Recibo recibo = new Recibo();
			recibo.setFactura(factura);
			recibo.setFechaPago(fechaActual);
			recibo.setFormaPago(cambioEstadoFacturaDto.getFormaPago());
			recibo.setTipoPago(cambioEstadoFacturaDto.getTipoPago());
			recibo.setValor(cambioEstadoFacturaDto.getValor());
			recibo.setSaldo(cambioEstadoFacturaDto.getSaldo());
			recibo.setNumeroComprobante(cambioEstadoFacturaDto.getNumeroComprobante());
			recibo.setFechaComprobante(cambioEstadoFacturaDto.getFechaComprobante());
			recibo.setEsConciliacion(false);
			recibo.setFechaCreacion(fechaActual);
			recibo.setIsActive(true);

			if (ObjectUtils.isNotEmpty(cambioEstadoFacturaDto.getIdBanco())) {
				recibo.setBanco(tipoBancoRepository.getReferenceById(cambioEstadoFacturaDto.getIdBanco()));
			}

			recibo = reciboRepository.save(recibo);

			if (cambioEstadoFacturaDto.getTipoPago().equals(TipoPago.ABONO)
					&& cambioEstadoFacturaDto.getFechaPromesaPago() != null) {
				this.crearFacturaPromesaPago(factura, cambioEstadoFacturaDto.getFechaPromesaPago());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return facturaMapper.toDto(facturaRepository.getReferenceById(cambioEstadoFacturaDto.getIdFactura()));
	}

	@Override
	public FacturaDTO anularFactura(AnularFacturaDto anularFacturaDto) {
		Instant fechaActual = Instant.now();
		Factura factura = facturaRepository.getReferenceById(anularFacturaDto.getIdFactura());
		factura.setEstado(EstadoFactura.ANULADA);
		factura.setJustificacionAnulacion(anularFacturaDto.getJustificacion());
		factura.setFechaModificacion(fechaActual);
		return facturaMapper.toDto(facturaRepository.save(factura));
	}

	private void crearFacturaPromesaPago(Factura factura, Instant fechaPromesaPago) {
		Instant fechaActual = Instant.now();
		FacturaPromesaPago facturaPromesaPago = new FacturaPromesaPago();
		facturaPromesaPago.setFactura(factura);
		facturaPromesaPago.setFechaPromesaPago(fechaPromesaPago);
		facturaPromesaPago.setFechaCreacion(fechaActual);
		facturaPromesaPago.setIsActive(true);
		facturaPromesaPagoRepository.save(facturaPromesaPago);
	}

	@Override
	public FacturaDTO aplicarDescuento(AplicarDescuentoFacturaDto aplicaDescuentoFacturaDto) {
		Instant fechaActual = Instant.now();
		FacturaDTO facturaDTO = new FacturaDTO();
		try {
			Factura factura = facturaRepository.getReferenceById(aplicaDescuentoFacturaDto.getIdFactura());

			if (aplicaDescuentoFacturaDto.getValor() > factura.getSaldo() || factura.getEstado() == EstadoFactura.PAGADA
					|| factura.getEstado() == EstadoFactura.ANULADA) {
				return null;
			}

			Double nuevoSaldo = factura.getSaldo() - aplicaDescuentoFacturaDto.getValor();

			if (nuevoSaldo == 0) {
				factura.setEstado(EstadoFactura.PAGADA);
			}

			factura.setSaldo(nuevoSaldo);
			factura.setFechaModificacion(fechaActual);
			facturaDTO = facturaMapper.toDto(facturaRepository.save(factura));

			DescuentoFactura descuento = new DescuentoFactura();
			descuento.setFactura(factura);
			descuento.setValor(aplicaDescuentoFacturaDto.getValor());
			descuento.setJustificacion(aplicaDescuentoFacturaDto.getJustificacion());
			descuento.setFechaCreacion(fechaActual);
			descuento.setIsActive(true);
			descuentoFacturaRepository.save(descuento);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return facturaDTO;
	}

	@Override
	public ResponseEntity<?> generarReporteRecibo(Long idRecibo) throws Exception {

		Recibo recibo = reciboRepository.getReferenceById(idRecibo);
		Factura factura = facturaRepository.getReferenceById(recibo.getFactura().getId());

		Contrato contrato = factura.getContrato();
		Sucursales sucursales = contrato.getSucursales();
		Empresa empresa = sucursales.getEmpresa();
		Cliente cliente = factura.getCliente();

		Date fechaActual = Calendar.getInstance().getTime();
		String fechaEmision = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fechaActual);
		String telefonosEmpresa = "";
		String telefonosCliente = "";

		Map<String, Object> mapParametros = new HashMap<String, Object>();
		mapParametros.put("SUBREPORT_DIR", "reportes/recibo/");
		mapParametros.put("emp_logo", "logo-insysred.jpg");
		mapParametros.put("emp_direccion", empresa.getDireccion());
		mapParametros.put("emp_email", empresa.getCorreo());

		if (ObjectUtils.isNotEmpty(empresa.getTelefonoFijo())) {
			telefonosEmpresa += empresa.getTelefonoFijo() + " ";
		}
		if (ObjectUtils.isNotEmpty(empresa.getTelefonoMovil())) {
			telefonosEmpresa += empresa.getTelefonoMovil();
		}
		mapParametros.put("emp_telefonos", telefonosEmpresa);

		Calendar calendarReporte = Calendar.getInstance();
		calendarReporte.setTime(Date.from(recibo.getFechaCreacion()));

		String fechaRecibo = new SimpleDateFormat("EEEEE", Locale.forLanguageTag("es-ES"))
				.format(calendarReporte.getTime()).toUpperCase() + " "
				+ new SimpleDateFormat("dd").format(calendarReporte.getTime()) + " DE "
				+ new SimpleDateFormat("MMMM", Locale.forLanguageTag("es-ES")).format(calendarReporte.getTime())
						.toUpperCase()
				+ " DEL " + new SimpleDateFormat("YYYY").format(calendarReporte.getTime());

		if (ObjectUtils.isNotEmpty(cliente.getTelfCelular())) {
			telefonosCliente += cliente.getTelfCelular() + " ";
		}
		if (ObjectUtils.isNotEmpty(cliente.getTelfFijo())) {
			telefonosCliente += cliente.getTelfFijo();
		}

		ReporteReciboDTO reporte = new ReporteReciboDTO();
		reporte.setIdRecibo(recibo.getId().toString());
		reporte.setFechaRecibo(fechaRecibo);
		reporte.setNombreCliente(cliente.getNombres() + " " + cliente.getApellidos());
		reporte.setIdentificacion(cliente.getIdentificacion());
		reporte.setSexo(cliente.getSexo().toString());
		reporte.setDireccion(contrato.getUbicacion() != null ? contrato.getUbicacion() : "-");
		reporte.setTelefonos(telefonosCliente);
		reporte.setCodigoActivacion("-");
		reporte.setUsuarioEmision("J. Chancay");
		reporte.setFechaEmision(fechaEmision);
		reporte.setTotalRecibo(recibo.getValor());

		List<ReporteReciboDetalleDTO> detalles = new ArrayList<ReporteReciboDetalleDTO>();
		ReporteReciboDetalleDTO det = new ReporteReciboDetalleDTO();
		det.setPrecio(recibo.getValor());
		det.setDescripcion("");
		for (DetalleFactura item : factura.getDetalleFacturas()) {
			if (ObjectUtils.isNotEmpty(item.getPlan())) {
				det.setDescripcion(det.getDescripcion() + " - " + item.getPlan().getDescripcion().toUpperCase());
			} else if (ObjectUtils.isNotEmpty(item.getRubro())) {
				det.setDescripcion(det.getDescripcion() + " - " + item.getRubro().getNombre().toUpperCase());
			}
		}
		if (recibo.getTipoPago().equals(TipoPago.ABONO)) {
			det.setDescripcion(
					det.getDescripcion() + " - (SALDO: $" + String.format("%,.2f", recibo.getSaldo()) + " USD)");
		}

		reporte.setFormaPago(recibo.getFormaPago().toString());
		if (recibo.getFormaPago().equals(FormaPago.TRANSFERENCIA) && ObjectUtils.isNotEmpty(recibo.getBanco())) {
			reporte.setFormaPago(reporte.getFormaPago() + " - " + recibo.getBanco().getNombre() + " - No."
					+ recibo.getNumeroComprobante() + " - " + recibo.getFechaComprobante());
		}

		detalles.add(det);

		reporte.setDetalles(detalles);

		File file = null;
		try {
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(reporte);
			file = jasperReportService.generarReporteJasper("reportes/recibo/reporte_factura_pago.jasper", "recibo_",
					".pdf", mapParametros, data);

			if (file != null) {
				byte[] arrFile = null;

				try {
					InputStream is = new FileInputStream(file);
					arrFile = IOUtils.toByteArray(is);
					is.close();

					return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/pdf")
							.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(arrFile.length))
							.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "recibo.pdf")
							.body(arrFile);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				} finally {
					try {
						if (Objects.nonNull(file)) {
							Files.deleteIfExists(file.toPath());
							file = null;
							arrFile = null;
						}
					} catch (IOException e) {
						e.printStackTrace();
						throw e;
					}
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
