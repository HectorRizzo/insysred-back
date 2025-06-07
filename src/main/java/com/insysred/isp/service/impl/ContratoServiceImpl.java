package com.insysred.isp.service.impl;

import com.insysred.isp.dto.CambioEstadoContratoDto;
import com.insysred.isp.dto.ContratoDto;
import com.insysred.isp.dto.ContratoNewDto;
import com.insysred.isp.dto.RoutersDto;
import com.insysred.isp.dto.ReporteContratoDTO;
import com.insysred.isp.entities.*;
import com.insysred.isp.enums.EstadoContratoEnum;
import com.insysred.isp.enums.TipoDocumento;
import com.insysred.isp.filtros.FiltroContrato;
import com.insysred.isp.mapper.ContratoMapper;
import com.insysred.isp.repository.*;
import com.insysred.isp.service.declare.ContratoServide;
import com.insysred.isp.util.ConnectMicrotik;
import me.legrange.mikrotik.ApiConnection;
import com.insysred.isp.service.declare.JasperReportService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
public class ContratoServiceImpl implements ContratoServide {

  @Autowired
  private ContratoRepository contratoRepository;

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private RouterRepository routerRepository;

  @Autowired
  private PlanesRepository planesRepository;

  @Autowired
  private ContratoMapper contratoMapper;

  @Autowired
  private SucursalRepository sucursalRepository;

  @Autowired
  private ConnectMicrotik connectMicrotik;
	@Autowired
	private JasperReportService jasperReportService;

  @Override
  public ContratoDto guardarContrato(ContratoNewDto contratoNewDto) {
    Cliente cliente = clienteRepository.getReferenceById(contratoNewDto.getCliente());
    Routers routers = routerRepository.getReferenceById(contratoNewDto.getServidor());
    Planes planes = planesRepository.getReferenceById(contratoNewDto.getPlan());
    Sucursales sucursales = sucursalRepository.getReferenceById(contratoNewDto.getSucursal());
    Contrato contrato = new Contrato();

    contrato.setCliente(cliente);
    contrato.setSucursales(sucursales);
    contrato.setServidor(routers);
    contrato.setPlan(planes);
    contrato.setLongitud(contratoNewDto.getLongitud());
    contrato.setLatitud(contratoNewDto.getLatitud());
    contrato.setUbicacion(contratoNewDto.getUbicacion());
    contrato.setReferencia(contratoNewDto.getReferencia());
    contrato.setIp(contratoNewDto.getIp());
    contrato.setMac(contratoNewDto.getMac());
    contrato.setFechaCrea(Instant.now());
    contrato.setFechaContrato(Instant.now());
    contrato.setIsActive(true);
	Optional<EstadoContrato> estadoContratoActivo = contratoRepository.getEstadoContratoByNombre(EstadoContratoEnum.ACTIVO.name());
      estadoContratoActivo.ifPresent(contrato::setEstadoContrato);
    ContratoDto contratoDTO = contratoMapper.toDto(contratoRepository.save(contrato));
    //crearQueue(contratoDTO.getIp(), "vlan1", contratoDTO.getNumContrato());
    crearIP(contratoDTO.getIp(), "vlan1", contratoDTO.getNumContrato());
    return contratoDTO;
  }

  @Override
  public ContratoDto cmbEstadoContrato(CambioEstadoContratoDto cambioEstadoContratoDto) throws Exception {
    ContratoDto contratoDTO = new ContratoDto();
    try {
      Optional<Contrato> contrato = contratoRepository.getContratoByNum(cambioEstadoContratoDto.getIdContrato());
	  if(!contrato.isPresent()){
		  throw new Exception("Contrato no encontrado");
	  }
      contrato.get().setEstadoContrato(cambioEstadoContratoDto.getEstado());
	  contratoDTO = contratoMapper.toDto(contratoRepository.save(contrato.get()));

    } catch (Exception e) {
      e.printStackTrace();
	  throw new Exception(e.getMessage());
    }


    return contratoDTO;
  }

  @Override
  public List<ContratoDto> getAllContratos() {
    return contratoMapper.toDto(contratoRepository.findAll());
  }

  public void crearQueue(String ip, String interfaz, Long idContrato) {
    try {

      String command = "/ip/arp/add address=" + ip + " interface=" + interfaz + " comment=" + idContrato;
      //   List<Map<String, String>> response = apiConnection.execute(command);
      // System.out.println(response.toString());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public Page<Contrato> getAllContratosClientes(PageRequest paginaRequest, String filtro) {
    return contratoRepository.findAll(FiltroContrato.contieneTexto(filtro), paginaRequest);
  }

  public void crearIP(String ip, String interfaz, Long idContrato){
    try {
      RoutersDto routersDto = new RoutersDto();
      routersDto.setIp("45.4.88.130");
      routersDto.setPuerto(16357);
      routersDto.setUsuario("developer");
      routersDto.setPassword("G9b041I[#k(^");
      ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
      String command = "/ip/arp/add address="+ip+" interface="+interfaz+" comment="+idContrato;
         List<Map<String, String>> response = apiConnection.execute(command);
       System.out.println(response.toString());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

	@Override
	public ResponseEntity<?> generarReporteContrato(Long idContrato) throws Exception {
		Contrato contrato = contratoRepository.getReferenceById(idContrato);

		if (ObjectUtils.isNotEmpty(contrato)) {
			Sucursales sucursales = contrato.getSucursales();
			Empresa empresa = sucursales.getEmpresa();
			RepresentanteLegal representante = empresa.getRepresentanteLegal();
			Cliente cliente = contrato.getCliente();

			String telefonosEmpresa = "";
			String telefonosCliente = "";

			Date fechaActual = Calendar.getInstance().getTime();
			Calendar calendarReporte = Calendar.getInstance();
			calendarReporte.setTime(Date.from(contrato.getFechaContrato()));

			String fechaContrato = new SimpleDateFormat("EEEEE", Locale.forLanguageTag("es-ES"))
					.format(calendarReporte.getTime()).toUpperCase() + " "
					+ new SimpleDateFormat("dd").format(calendarReporte.getTime()) + " DE "
					+ new SimpleDateFormat("MMMM", Locale.forLanguageTag("es-ES")).format(calendarReporte.getTime())
							.toUpperCase()
					+ " DEL " + new SimpleDateFormat("YYYY").format(calendarReporte.getTime());

			if (ObjectUtils.isNotEmpty(empresa.getTelefonoFijo())) {
				telefonosEmpresa += empresa.getTelefonoFijo() + " ";
			}
			if (ObjectUtils.isNotEmpty(empresa.getTelefonoMovil())) {
				telefonosEmpresa += empresa.getTelefonoMovil();
			}

			if (ObjectUtils.isNotEmpty(cliente.getTelfCelular())) {
				telefonosCliente += cliente.getTelfCelular() + " ";
			}
			if (ObjectUtils.isNotEmpty(cliente.getTelfFijo())) {
				telefonosCliente += cliente.getTelfFijo();
			}

			Map<String, Object> mapParametros = new HashMap<String, Object>();
			mapParametros.put("emp_logo", "logo-insysred.jpg");
			mapParametros.put("emp_direccion", empresa.getDireccion());
			mapParametros.put("emp_email", empresa.getCorreo());
			mapParametros.put("emp_telefonos", telefonosEmpresa);
			mapParametros.put("idContrato", contrato.getNumContrato());
			mapParametros.put("fechaContrato", fechaContrato);

			mapParametros.put("representante_nombre", representante.getNombres() + " " + representante.getApellidos());
			mapParametros.put("representante_cargo", representante.getCargo() + " " + empresa.getNombreComercial());
			mapParametros.put("representante_identificacion", representante.getTipoDocumento() + ": " + representante.getIdentificacion());

			ReporteContratoDTO reporte = new ReporteContratoDTO();
			reporte.setNombreEmpresaCliente(empresa.getNombreComercial());
			reporte.setIdentificacionCliente(cliente.getIdentificacion());
			reporte.setNombreCliente(cliente.getNombres() + " " + cliente.getApellidos());
			reporte.setDireccionCliente(ObjectUtils.isNotEmpty(contrato.getUbicacion()) ? contrato.getUbicacion() : "-");
			reporte.setReferenciaCliente(ObjectUtils.isNotEmpty(contrato.getReferencia()) ? contrato.getReferencia() : "-");
			reporte.setTelefonosCliente(telefonosCliente);
			reporte.setCorreoCliente(cliente.getEmail());
			reporte.setCantonCliente(sucursales.getCanton().getNombre());
			reporte.setProvinciaCliente(sucursales.getCanton().getProvincia().getNombre());

			reporte.setContratoPrestacion("Intervienen en la celebración del presente contrato de prestación de servicios, por una parte el señor"
					+ " JACOBO HIPOLITO CHANCAY CEPEDA, en su calidad de Gerente General de INSYSRED S.A., a quien"
					+ " podrá denominarse simplemente como \"EL PERMISIONARIO\", \"EL PROVEEDOR\" o \"INSYSRED S.A.\", y"
					+ " por otra parte");

			if (cliente.getTipoDocumento() != TipoDocumento.RUC) {
				reporte.setContratoPrestacion(reporte.getContratoPrestacion() + " " + reporte.getNombreCliente());
			} else {
				reporte.setContratoPrestacion(reporte.getContratoPrestacion() + " " + cliente.getRazonSocial() + ", representada por " + reporte.getNombreCliente());
			}
			reporte.setContratoPrestacion(reporte.getContratoPrestacion() + ", con número de cédula/RUC " + reporte.getIdentificacionCliente() + ", con email "
					+ reporte.getCorreoCliente() + ", domiciliado en " + reporte.getDireccionCliente() + ", cantón " + reporte.getCantonCliente()
					+ ", provincia " + reporte.getProvinciaCliente() + ", a quien podrá denominarse simplemente como \"EL CLIENTE\", quienes de manera"
					+ " libre y por mutuo acuerdo y voluntariamente celebran el presente contrato de prestación de servicios"
					+ " contenido en las siguientes cláusulas:");

			reporte.setClausulaPrimera("El PROVEEDOR se encuentra autorizado para prestar Servicios de Valor Agregado de Acceso a Internet de acuerdo"
					+ " a la Resolución No. 362-08-CONATEL-2011 del 28 de abril de 2011 y el Permiso para la Prestación de Servicios de Valor Agregado"
					+ " de fecha 28 de junio de 2011 inscrito en el Tomo 93 a Fojas 9329 del Registro Público de Telecomunicaciones, en la misma fecha.");

			reporte.setClausulaSegunda("El presente contrato tiene por objeto que EL PROVEEDOR proporcione a EL CLIENTE el acceso a la red internet"
					+ " conforme a las características pactadas, que se describen en los Anexos Técnico y Comercial que debidamente firmados por las"
					+ " partes, son integrantes de este instrumento. Las partes aceptan que este instrumento constituya un contrato marco general,"
					+ " y que, en adelante los servicios, cambios en los servicios, y cualquier otra modificación que se implemente; se realizará mediante"
					+ " la suscripción de nuevos Anexos Técnico (s) y Comercial (es) correspondientes, que debidamente firmados por las partes, serán"
					+ " integrantes de este Contrato, que se seguirán las condiciones generales de este instrumento con las especificaciones de los"
					+ " respectivos Anexos que suscriban las partes. EL PROVEEDOR guardará cronológicamente, para efectos de prueba, los Anexos que"
					+ " llegaren a suscribirse entre las partes.");

			reporte.setClausulaTercera("a) El precio acordado por la instalación y puesta en funcionamiento por el Servicio de Acceso a Internet es el que"
					+ " consta en el ANEXO 2 (comercial), y que firmado por las partes, es integrante del presente contrato. b) El precio mensual acordado"
					+ " por la prestación del Servicio de Acceso a Internet, es el que corresponde al Plan contratado, y cuyo valor mensual y descripción"
					+ " consta en el ANEXO 2 (Comercial), que debidamente firmado por las partes, es integrante del presente contrato. c) El Plan contratado"
					+ " se pagará en mensualidades, pagaderas por el CLIENTE a EL PROVEEDOR por mes adelantado, dentro de los 5 días primeros días de cada"
					+ " mes calendario; previo la entrega de la factura por el servicio contratado. En caso que, el CLIENTE no cancele los valores hasta el"
					+ " vigésimo día dentro del mes calendario que se encuentre en curso, EL PROVEEDOR tiene la facultad de suspender la prestación del"
					+ " servicio en cualquier momento, de no producirse el pago del plan dentro del plazo antes señalado, sin que implique terminación de"
					+ " contrato. En los casos que corresponda, se cobrarán los rubros antes indicados y la mensualidad en lo proporcional del mes de"
					+ " facturación en curso. EL PROVEEDOR respetará la intervención de operadores de servicios finales o de servicio portador para el acceso"
					+ " de sus abonados; no está permitida la creación o levantamiento de redes de acceso directas a sus abonados; salvo obtención del"
					+ " contrato habilitante (reventa) para incluir en un solo rubro mensual el cobro de los servicios en los planes de acceso a Internet que"
					+ " ofrezca. En este último caso el PERMISIONARIO presentará, el contrato de reventa debidamente registrado en la SENATEL, tanto al"
					+ " organismo técnico de control en caso de inspección, o al abonado que así lo requiera.");

			reporte.setClausulaCuarta("1.- A recibir el servicio de acuerdo a los términos estipulados en el presente contrato. 2.- A un reconocimiento"
					+ " económico que corresponda al tiempo que el servicio no ha estado disponible, cuando la causa fuese imputable al prestador del"
					+ " servicio. 3.- A que no se varíe el precio estipulado en el contrato o sus Anexos, mientras dure la vigencia del mismo o no se cambien"
					+ " las condiciones de la prestación a través de la suscripción de nuevos Anexos Técnico (s) y Comercial (es). 4.- A reclamar respecto"
					+ " de la calidad del servicio, cobros no contratados, elevaciones de tarifas, irregularidades en relación a la prestación del servicio"
					+ " ante la Defensoría del Pueblo y/o Superintendencia de Telecomunicaciones. 5.- A reclamar de manera integral por los problemas de"
					+ " calidad tanto del Acceso a la Red Internet, así como por las deficiencias en el enlace provisto para brindar el servicio."
					+ " En particular en los casos en que aparezca EL PROVEEDOR como revendedor del servicio portador. En este último caso, responderá"
					+ " EL PROVEEDOR plenamente a su abonado conforme a la Ley Orgánica de Defensa del Consumidor, (independientemente de los acuerdos"
					+ " existentes entre los operadores o las responsabilidades ante las autoridades de telecomunicaciones). 6.- EL PROVEEDOR reconoce a sus"
					+ " clientes todos los derechos que se encuentran determinados en la Ley Orgánica de Defensa del Consumidor y su Reglamento; el Reglamento"
					+ " para la prestación del Servicios de Valor Agregado y la Resolución No. 216-09-CONATEL-2009.");

			reporte.setClausulaQuinta("El presente contrato, tendrá un plazo de vigencia de 12 meses, contados a partir de la fecha de suscripción del mismo."
					+ " En caso que, ninguna de las partes, notifique su deseo de dar por terminado el contrato, con 30 días de anticipación a la fecha de su"
					+ " vencimiento, se renovará automáticamente, así como de manera sucesiva, en iguales términos y condiciones que se encontraren vigentes.");

			reporte.setClausulaSexta("EL PROVEEDOR cumplirá los estándares de calidad emitidos y verificados por los organismos regulatorios y de control de"
					+ " las telecomunicaciones en el Ecuador, no obstante detalla que prestará sus servicios al cliente con los niveles de calidad especificados"
					+ " en el Anexo 1 (Técnico) que debidamente firmado por las partes forma parte integrante de este contrato. Así como declara que el"
					+ " SERVICIO DE INTERNET DEDICADO tenderá: Disponibilidad 99,6% mensual calculada sobre la base de 720 horas al mes. Para el cálculo de no"
					+ " disponibilidad del servicio no se considerará el tiempo durante el cual no se lo haya podido prestar debido a circunstancias de caso"
					+ " fortuito o fuerza mayor o completamente ajenas al proveedor. Para trabajos en caso de mantenimiento, en la medida de lo posible,"
					+ " deberán ser planificados en períodos de 4 horas después de la media noche, debiéndose notificar previamente el tiempo de no"
					+ " disponibilidad por mantenimiento y siguiendo lo previsto en la Ley Orgánica de Defensa del Consumidor. El PROVEEDOR recibirá"
					+ " requerimientos del Cliente, las 24 horas del día, a través de los números 0984789568 - 0995974781, o los que se haga conocer en el"
					+ " futuro a los abonados; o mediante e-mail: insysred@live.com y lo registrará en el sistema haciendo la apertura de un registro y lo"
					+ " dirigirá al personal indicado. EL PROVEEDOR realizará el seguimiento de los requerimientos y el cumplimiento de la corrección del"
					+ " problema, en un plazo máximo de 24 horas contadas desde que se notifique el problema. Las características técnicas y de calidad de"
					+ " servicio constan en el Anexo 1 que debidamente firmado por las partes es integrante del presente contrato y cumple con lo exigido en"
					+ " la Resolución 216-09-CONATEL-2009.");

			reporte.setClausulaSeptima("El mantenimiento preventivo y correctivo, ordinario y extraordinario corren por cuenta de EL PERMISIONARIO; mientras"
					+ " que el CLIENTE será responsable del manejo, mantenimiento, reparación y/o adecuación de los equipos que son parte de la red del CLIENTE."
					+ " El CLIENTE, es responsable que las instalaciones eléctricas dentro de su infraestructura cuenten con energía eléctrica aterrizada"
					+ " y estabilizada; adicionalmente, el(los) equipo(s) que EL PROVEEDOR instale en la ubicación contratada por el CLIENTE debe(n) ser"
					+ " conectados a un toma de UPS provista por el CLIENTE. En casos de interrupción en la prestación del servicio se reintegrará o"
					+ " compensará al abonado conforme a la Ley Orgánica de Defensa del Consumidor y su Reglamento.");

			reporte.setClausulaOctava("El presente contrato terminará por las siguientes causas: a) Por mutuo acuerdo de las partes b) Por incumplimiento"
					+ " de las obligaciones contractuales. c) Por vencimiento del plazo de vigencia previa comunicación de alguna de las partes; d) Por causas"
					+ " de fuerza mayor o caso fortuito debidamente comprobado; e) Por falta de pago de 2 mensualidades por parte del Cliente. f) El Cliente"
					+ " podrá dar por terminado unilateralmente el contrato en cualquier tiempo, previa notificación por escrito con la menos quince días de"
					+ " anticipación a la finalización del período en curso, no obstante el Cliente tendrá la obligación de cancelar los saldos pendientes"
					+ " únicamente por los servicios prestados hasta la fecha de la terminación unilateral del contrato, así como los valores adeudados por"
					+ " la adquisición de los bienes necesarios para la prestación del servicio de ser el caso. g) Si el CLIENTE utiliza los servicios"
					+ " contratados para fines distintos a los convenidos, o si los utiliza en prácticas contrarias a la ley, las buenas costumbres, la moral"
					+ " o cualquier forma que perjudique a EL PROVEEDOR.");

			reporte.setClausulaNovena("El PROVEEDOR se obliga a lo siguiente:");
			reporte.setClausulaNovena(reporte.getClausulaNovena() + "\n\t1. Proporcionar el mantenimiento preventivo y correctivo, del servicio y la configuración respectiva.");
			reporte.setClausulaNovena(reporte.getClausulaNovena() + "\n\t2. Las determinadas en la Resolución 216-09-CONATEL-2009, o las que emitiere el órgano regulador.");
			reporte.setClausulaNovena(reporte.getClausulaNovena() + "\n\t3. Al pago de indemnizaciones por no cumplimiento de niveles de calidad estipulados en el presente contrato.");
			reporte.setClausulaNovena(reporte.getClausulaNovena() + "\n\t4. Garantizarán la privacidad y confidencialidad de las telecomunicaciones en el servicio prestado al CLIENTE.");
			reporte.setClausulaNovena(reporte.getClausulaNovena() + "\n\t5. Las que constan en le Reglamento para la Prestación de Servicios de Valor Agregado y sus modificaciones.");
			reporte.setClausulaNovena(reporte.getClausulaNovena() + "\nEl CLIENTE se obliga a lo siguiente:");
			reporte.setClausulaNovena(reporte.getClausulaNovena() + "\n\t1. Manejo, mantenimiento, reparación y/o adecuación de los equipos que son parte de su red.");
			reporte.setClausulaNovena(reporte.getClausulaNovena() + "\n\t2. Que las instalaciones eléctricas dentro de su infraestructura cuenten con energía eléctrica aterrizada y estabilizada.");
			reporte.setClausulaNovena(reporte.getClausulaNovena() + "\n\t3. Que el (los) equipo(s) sean conectado (s) a un toma de UPS provista por este último.");
			reporte.setClausulaNovena(reporte.getClausulaNovena() + "\n\t4. Pago oportuno e íntegro de los valores pactados en el presente contrato.");

			reporte.setClausulaDecima("Si por alguna razón se reformara el Reglamento o la Ley que regula la prestación de los servicios de valor agregado e"
					+ " internet, el presente contrato podrá ser modificado en función de los cambios que se dieren previa aprobación y registro de la SENATEL.");

			reporte.setClausulaUndecima("EL CLIENTE asume la responsabilidad por los actos de sus empleados, contratistas o subcontratistas por el mal uso que"
					+ " eventualmente diere a los servicios que se les preste; en especial si se usare los servicios o enlaces prestados en actividades"
					+ " contrarias a las leyes y regulaciones de telecomunicaciones. Por su parte EL PROVEEDOR tendrá responsabilidad por la debida prestación"
					+ " del servicio contratado en las características y estándares del presente contrato y las señaladas en las Leyes y regulación vigente.");

			reporte.setClausulaDuodecima("Independientemente del juzgamiento de infracciones conforme a Ley Orgánica de Defensa del Consumidor, las partes"
					+ " acuerdan que podrán solucionar sus controversias a través de la mediación, en el Centro de Mediación y Arbitraje de la Cámara de"
					+ " Comercio de Guayaquil. Si la mediación no llegare a producirse las partes acuerdan expresamente que se someten a un Arbitraje en"
					+ " Derecho ante el mismo centro, para lo cual renuncian a la jurisdicción ordinaria, y se someten expresamente al arbitraje, obligándose"
					+ " a acatar el laudo que expida el Tribunal Arbitral y se comprometen a no interponer ningún tipo de recurso en contra del laudo dictado,"
					+ " a más de los permitidos en la ley, para lo cual presentan las respectivas copias de cédulas de identidad y ciudadanía para el"
					+ " reconocimiento de firmas respectivo. Para constancia de lo anterior las partes firman en tres ejemplares del mismo tenor, en el cantón"
					+ " DURAN, a los 28 días del mes de Diciembre de 2023.");

			File file = null;
			try {
				ArrayList<Object> data = new ArrayList<Object>();
				data.add(reporte);
				file = jasperReportService.generarReporteJasper("reportes/contrato/reporte_contrato.jasper", "contrato_",
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
								.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "contrato.pdf")
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

		return null;
	}

	@Override
	public List<EstadoContrato> getEstadosContrato() {
		try{
			return contratoRepository.getEstadosContrato();
		}catch (Exception e){
			throw new RuntimeException("Error al obtener los estados de contrato", e);
		}
	}
}
