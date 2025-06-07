package com.insysred.isp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.insysred.isp.dto.IdMicrotikParamDto;
import com.insysred.isp.dto.InterfazMikroDto;
import com.insysred.isp.dto.MkservervlanDto;
import com.insysred.isp.dto.PlanesDto;
import com.insysred.isp.dto.RoutersDto;
import com.insysred.isp.dto.microtik.VlanDto;
import com.insysred.isp.entities.InterfazMikro;
import com.insysred.isp.entities.Routers;
import com.insysred.isp.mapper.InterfazMikroMapper;
import com.insysred.isp.mapper.RouterMapper;
import com.insysred.isp.repository.InterfazMikroRepository;
import com.insysred.isp.repository.RouterRepository;
import com.insysred.isp.service.declare.MikroTikService;
import com.insysred.isp.util.ConnectMicrotik;
import com.insysred.isp.util.Encriptar;
import com.insysred.isp.util.SubnetCalculator;
import com.insysred.isp.util.ValidateMicrotik;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

@Service
public class MikroTikServiceImpl implements MikroTikService {

	@Autowired
	private ConnectMicrotik connectMicrotik;

	@Autowired
	private RouterRepository routersRepository;

	@Autowired
	private Encriptar encriptar;

	@Autowired
	private RouterMapper routerMapper;

	@Autowired
	private InterfazMikroRepository interfazMikroRepository;

	@Autowired
	private InterfazMikroMapper interfazMikroMapper;

	@Autowired
	private ValidateMicrotik validateMicrotik;

//	@Override
//	public List<Map<String, String>> getRouterInfo() throws MikrotikApiException {
//		List<Map<String, String>> result = null;
//		RoutersDto routersDto = new RoutersDto();
//		routersDto.setIp("45.4.88.130");
//		routersDto.setPuerto(16357);
//		routersDto.setUsuario("developer");
//		routersDto.setPassword("G9b041I[#k(^");
//		ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
//
//		result = apiConnection.execute("/system/resource/print");
//
//		return result;
//	}

	@Override
	public List<Map<String, String>> getRouterInfo() throws MikrotikApiException {
		List<Map<String, String>> result = null;
		RoutersDto routersDto = new RoutersDto();
		routersDto.setIp("45.4.88.130");
		routersDto.setPuerto(16357);
		routersDto.setUsuario("developer");
		routersDto.setPassword("G9b041I[#k(^");
		ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);

		result = apiConnection.execute("/interface/print");

		return result;
	}

	@Override
	public List<Map<String, String>> getARP() throws MikrotikApiException {
		RoutersDto routersDto = new RoutersDto();
		routersDto.setIp("45.4.88.130");
		routersDto.setPuerto(16357);
		routersDto.setUsuario("developer");
		routersDto.setPassword("G9b041I[#k(^");
		String interfaz = "vlan1";
		ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
		String command = "/ip/arp/print";
		List<Map<String, String>> result = apiConnection.execute(command);
		System.out.println(result);
		return result;
	}

	public void agregarServidorIP() {
		// try {
		// Ejecutar el comando para agregar la dirección IP
		// String command = "/ip/address/add address=192.168.88.14/24 comment=aaa
		// interface=vlan78";
		String command = "/ip/address/add address=192.168.88.15/24 comment=Joseph interface=vlan78";

		// Ejecutar el comando para agregar la dirección IP
		// List<Map<String, String>> response = apiConnection.execute(command);
		// Procesar la respuesta
		/*
		 * for (Map<String, String> entry : response) { System.out.println("Resultado: "
		 * + entry.get("message")); }
		 */
		/*
		 * } catch (MikrotikApiException e) { e.printStackTrace(); }
		 */
	}

	public void agregarIpARP() {
		try {
			String command = "/ip/arp/add address=45.4.88.145 interface=vlan1 comment=TesterARP";
			// List<Map<String, String>> response = apiConnection.execute(command);
			// System.out.println(response.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void crearReglaFirewall(String regla) {

	}

	// Se añade con cada plan
	@Override
	public List<Map<String, String>> setQueue() throws MikrotikApiException {

		String queueListName = "testGustav";
		String parentQueue = "vlan1";
		String packetMark = "no-mark";
		String command = "/queue/simple/add name=" + queueListName + " parent=" + parentQueue + " packet-mark="
				+ packetMark;

		RoutersDto routersDto = new RoutersDto();
		routersDto.setIp("45.4.88.130");
		routersDto.setPuerto(16357);
		routersDto.setUsuario("developer");
		routersDto.setPassword("G9b041I[#k(^");
		ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
		List<Map<String, String>> response = apiConnection
				.execute("/queue/tree/add name=testKevin  parent=vlan1 packet-mark=no-mark");
		System.out.println(response);
// Procesar la respuesta
		for (Map<String, String> entry : response) {
			String message = entry.get("message");
			System.out.println("Resultado: " + message);

			if (message != null && message.toLowerCase().contains("failure")) {
				// Imprimir información adicional o realizar acciones en caso de error
				System.out.println("Error al agregar la regla a la cola.");
			}
		}

		return response;
	}

	@Override
	public List<Map<String, String>> updateQueue() throws MikrotikApiException {
		String command = "/queue/tree/set .id=*1000009 name='Plan plus' max-limit=25M";
		try {
			RoutersDto routersDto = new RoutersDto();
			routersDto.setIp("45.4.88.130");
			routersDto.setPuerto(16357);
			routersDto.setUsuario("developer");
			routersDto.setPassword("G9b041I[#k(^");
			ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
			List<Map<String, String>> response = apiConnection.execute(command);
			System.out.println(response);
			// Procesa la respuesta si es necesario
		} catch (MikrotikApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Añade un queue dependiendo de los planes
	@Override
	public List<Map<String, String>> newQueue(PlanesDto planesDto) throws MikrotikApiException {
		// Update dependiendo de la sucursal
		RoutersDto routersDto = new RoutersDto();
		routersDto.setIp("45.4.88.130");
		routersDto.setPuerto(16357);
		routersDto.setUsuario("developer");
		routersDto.setPassword("G9b041I[#k(^");
		ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
		// Datos del contrato
		List<Map<String, String>> response = apiConnection.execute("/queue/tree/add name='" + planesDto.getName()
				+ "' parent=vlan1 packet-mark=no-mark  max-limit=" + planesDto.getMegabytes() + "M");
		System.out.println(response);
// Procesar la respuesta
		for (Map<String, String> entry : response) {
			String message = entry.get("message");
			System.out.println("Resultado: " + message);

			if (message != null && message.toLowerCase().contains("failure")) {
				// Imprimir información adicional o realizar acciones en caso de error
				System.out.println("Error al agregar la regla a la cola.");
			}
		}

		return response;
	}

	@Override
	public List<Map<String, String>> getInterfacesByRouter(IdMicrotikParamDto idRouter) throws MikrotikApiException {
		List<Map<String, String>> result = new ArrayList<>();
		try {
			Routers router = routersRepository.getReferenceById(idRouter.getIdServidor());

			if (router != null) {
				router.setPassword(encriptar.decrypt(router.getPassword()));
				ApiConnection apiConnection = connectMicrotik.apiConnection(routerMapper.toDto(router));
				result = apiConnection.execute("/interface/print");
				List<InterfazMikro> interfazMikroDtoList = new ArrayList<>();
				if (result != null) {
					router.setPassword(encriptar.encrypt(router.getPassword()));
					for (int i = 0; i < result.size(); i++) {
						Map<String, Object> mapa = new HashMap<>();
						mapa.put("data", result.get(i));
						Gson gson = new Gson();
						InterfazMikroDto responseObject = gson.fromJson(obtenerJsonInterno(mapa),
								InterfazMikroDto.class);

						if (!existeInterfaz(responseObject, router.getId())) {
							responseObject.setRouter(routerMapper.toDto(router));
							interfazMikroDtoList.add(interfazMikroMapper.toEntity(responseObject));
						}
						// interfazMikroDtoList.add(interfazMikroMapper.toEntity(responseObject));
					}
					interfazMikroRepository.saveAll(interfazMikroDtoList);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String obtenerJsonInterno(Map<String, Object> mapa) {
		Map<String, String> datos = (Map<String, String>) mapa.get("data");
		Gson gson = new Gson();
		String json = gson.toJson(datos);
		return json;
	}

	public Boolean existeInterfaz(InterfazMikroDto idInterfaz, Long idRouter) {
		Boolean exite = false;
		try {
			List<InterfazMikro> interfazMikroList = interfazMikroRepository
					.getInterfazMikroByRouter(idInterfaz.getId().toString(), idRouter);
			if (interfazMikroList.size() > 0) {
				exite = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exite;
	}

	@Override
	public List<Map<String, String>> addVlan(VlanDto vlanDto) {
		List<Map<String, String>> result = new ArrayList<>();
		try {
			Routers router = routersRepository.getReferenceById(1L);
			if (router != null) {
				List<InterfazMikro> interfazMikroList = interfazMikroRepository.getInterfazMikroByName(1L,
						vlanDto.getNombre());
				if (interfazMikroList.size() == 0) {
					router.setPassword(encriptar.decrypt(router.getPassword()));
					ApiConnection apiConnection = connectMicrotik.apiConnection(routerMapper.toDto(router));
					String command = "/interface/print where name=" + vlanDto.getNombre();
					result = apiConnection.execute(command);
					if (result.size() == 0) {
						String commandNew = "/interface/vlan/add name=" + vlanDto.getNombre() + " comment='"
								+ vlanDto.getComentario() + "' interface=" + vlanDto.getInterfaz() + " vlan-id=14";
						result = apiConnection.execute(commandNew);
						String commandGet = "/interface/print where name=" + vlanDto.getNombre();
						result = apiConnection.execute(commandGet);
						List<InterfazMikro> interfazMikroDtoList = new ArrayList<>();
						router.setPassword(encriptar.encrypt(router.getPassword()));
						for (int i = 0; i < result.size(); i++) {
							Map<String, Object> mapa = new HashMap<>();
							mapa.put("data", result.get(i));
							Gson gson = new Gson();
							InterfazMikroDto responseObject = gson.fromJson(obtenerJsonInterno(mapa),
									InterfazMikroDto.class);
							responseObject.setRouter(routerMapper.toDto(router));
							interfazMikroDtoList.add(interfazMikroMapper.toEntity(responseObject));
						}
						interfazMikroRepository.saveAll(interfazMikroDtoList);
					}
				} else {
					System.out.println("Ya existe esta interfaz");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	@Override
	public List<Map<String, String>> addMangle(Long idServidor) throws MikrotikApiException {
		List<Map<String, String>> result = new ArrayList<>();
		try {
			Routers router = routersRepository.getReferenceById(idServidor);
			router.setPassword(encriptar.decrypt(router.getPassword()));
			ApiConnection apiConnection = connectMicrotik.apiConnection(routerMapper.toDto(router));
			String command = "/ip/firewall/mangle/add" + " chain=prerouting" + " src-address=192.168.25.25"
					+ " action=mark-packet" + " new-connection-mark=conn1" + " comment=username";
			System.out.println(command);

			result = apiConnection.execute(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> getMangle(Long id_router) throws MikrotikApiException {
		try {
			Routers routers = routersRepository.getReferenceById(id_router);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = new RoutersDto();
				routersDto.setIp(routers.getIp());
				routersDto.setPuerto(routers.getPuerto().intValue());
				routersDto.setUsuario(routers.getUsuario());
				routersDto.setPassword(encriptar.decrypt(routers.getPassword()));
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String command = "/ip/firewall/mangle/print";
				return apiConnection.execute(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<?> getMangle() throws MikrotikApiException {
		List<?> result = null;
		try {
			RoutersDto routersDto = new RoutersDto();
			routersDto.setIp("45.4.88.130");
			routersDto.setPuerto(16357);
			routersDto.setUsuario("developer");
			routersDto.setPassword("G9b041I[#k(^");
			ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
//			String command = "/ip/firewall/mangle/print";
			String command = "/ip/firewall/filter/print";
			result = apiConnection.execute(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> getARPRouter(Long id_router) throws MikrotikApiException {
		try {
			Routers routers = routersRepository.getReferenceById(id_router);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = new RoutersDto();
				routersDto.setIp(routers.getIp());
				routersDto.setPuerto(routers.getPuerto().intValue());
				routersDto.setUsuario(routers.getUsuario());
				routersDto.setPassword(encriptar.decrypt(routers.getPassword()));
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String command = "/ip/arp/print";
				return apiConnection.execute(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	@Override
//	public List<?> getInterfaces(Long id_router) throws MikrotikApiException {
//		try {
//			Routers routers = routersRepository.getReferenceById(id_router);
//			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
//				RoutersDto routersDto = new RoutersDto();
//				routersDto.setIp(routers.getIp());
//				routersDto.setPuerto(routers.getPuerto().intValue());
//				routersDto.setUsuario(routers.getUsuario());
//				routersDto.setPassword(encriptar.decrypt(routers.getPassword()));
//				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
//				return apiConnection.execute("/interface/print");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	@Override
	public List<?> getInterfaces(Long id_router) throws MikrotikApiException {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
//		try {
//			System.out.println("Detalles del router con ID " + id_router.toString() + ":");
//			RoutersDto routersDto = new RoutersDto();
//			routersDto.setIp("45.4.88.130");
//			routersDto.setPuerto(16357);
//			routersDto.setUsuario("developer");
//			routersDto.setPassword("G9b041I[#k(^");
//			routersDto.setId("*" + id_router.toString());
//			ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
//			String command = "/interface/print";
//			List<Map<String, String>> resultSrvr = apiConnection.execute(command); 
//			
//			for (Map<String, String> entry : resultSrvr) {
//				System.out.println("Detalles del router con ID " + entry.get(".id") + ":");
//                if (id_router.equals(entry.get(".id"))) {
////                    System.out.println("Detalles del router con ID " + routerId + ":");
//                    result.add(entry);
//                    break;
////                    for (Map.Entry<String, String> field : entry.entrySet()) {
////                        System.out.println(field.getKey() + ": " + field.getValue());
////                    }
//                }
//            }
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return result;
	}

	@Override
	public List<?> getInterfaces() throws MikrotikApiException {
		List<?> result = null;
//		try {
//			RoutersDto routersDto = new RoutersDto();
//			routersDto.setIp("45.4.88.130");
//			routersDto.setPuerto(16357);
//			routersDto.setUsuario("developer");
//			routersDto.setPassword("G9b041I[#k(^");
//			ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
//			String command = "/interface/print";
//			result = apiConnection.execute(command);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return result;
	}

	@Override
	public List<?> getQueues(Long id_router) throws MikrotikApiException {
		try {
			Routers routers = routersRepository.getReferenceById(id_router);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = new RoutersDto();
				routersDto.setIp(routers.getIp());
				routersDto.setPuerto(routers.getPuerto().intValue());
				routersDto.setUsuario(routers.getUsuario());
				routersDto.setPassword(encriptar.decrypt(routers.getPassword()));
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				return apiConnection.execute("/queue/tree/print");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<?> getQueues() throws MikrotikApiException {
		List<?> result = null;
		try {
			RoutersDto routersDto = new RoutersDto();
			routersDto.setIp("45.4.88.130");
			routersDto.setPuerto(16357);
			routersDto.setUsuario("developer");
			routersDto.setPassword("G9b041I[#k(^");
			ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
			String command = "/queue/tree/print";
			result = apiConnection.execute(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> getFirewall(Long id_router) throws MikrotikApiException {
		try {
			Routers routers = routersRepository.getReferenceById(id_router);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = new RoutersDto();
				routersDto.setIp(routers.getIp());
				routersDto.setPuerto(routers.getPuerto().intValue());
				routersDto.setUsuario(routers.getUsuario());
				routersDto.setPassword(encriptar.decrypt(routers.getPassword()));
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				return apiConnection.execute("/ip/firewall/address-list/print");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<?> getFirewall() throws MikrotikApiException {
		List<?> result = null;
		try {
			Routers routers = routersRepository.getReferenceById(1L);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = buildRouterInfo(routers);
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String command = "/ip/firewall/filter/print";
				result = apiConnection.execute(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> getServerRules(Long idServer) throws MikrotikApiException {
		List<?> result = null;
		try {
			Routers routers = routersRepository.getReferenceById(idServer);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = buildRouterInfo(routers);
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String command = "/ip/firewall/filter/print";
				result = apiConnection.execute(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> addServerRule(Long idServer) throws MikrotikApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getServerNats(Long idServer) throws MikrotikApiException {
		List<?> result = null;
		try {
			Routers routers = routersRepository.getReferenceById(idServer);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = buildRouterInfo(routers);
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String command = "/ip/firewal/nat/print";
				result = apiConnection.execute(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> addServerNat(Long idServer) throws MikrotikApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getServerVlan(Long idServer) throws MikrotikApiException {
		List<?> result = null;
		try {
			Routers routers = routersRepository.getReferenceById(idServer);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = buildRouterInfo(routers);
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String command = "/interface/vlan/print";
				result = apiConnection.execute(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> getServerIpAddress(Long idServer) throws MikrotikApiException {
		List<?> result = null;
		try {
			Routers routers = routersRepository.getReferenceById(idServer);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = buildRouterInfo(routers);
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String command = "/ip/address/print";
				result = apiConnection.execute(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Map<String, String>> addServerVlan(MkservervlanDto vlanDto){
		List<Map<String, String>> result = new ArrayList<>();
		try {
			Routers routers = routersRepository.getReferenceById(vlanDto.getIdRouter());
			if (routers != null) {
				RoutersDto routersDto = buildRouterInfo(routers);
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String vlanComnd = "/interface/vlan/add name=" + vlanDto.getNombre() + " comment='" + vlanDto.getComentario() + "' interface=" + vlanDto.getInterfaz() + " vlan-id=" + vlanDto.getMktVlanId();
				result = apiConnection.execute(vlanComnd);
				
				String cidrNotation = SubnetCalculator.convertToCIDRNotation(vlanDto.getMascara());
				System.out.println(cidrNotation);
				String ipListCmnd = "/ip/address/add address=" + vlanDto.getIp() + "/" + cidrNotation + " comment='" + vlanDto.getComentario() + "' interface=" +vlanDto.getNombre();
				System.out.println(ipListCmnd);
				result = apiConnection.execute(ipListCmnd);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	private RoutersDto buildRouterInfo(Routers routers) throws Exception {
		RoutersDto routersDto = new RoutersDto();
		routersDto.setIp(routers.getIp());
		routersDto.setPuerto(routers.getPuerto().intValue());
		routersDto.setUsuario(routers.getUsuario());
		routersDto.setPassword(encriptar.decrypt(routers.getPassword()));
		return routersDto;
	}

	@Override
	public List<?> getServerAddressList(Long idServer) throws MikrotikApiException {
		List<?> result = null;
		try {
			Routers routers = routersRepository.getReferenceById(idServer);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = buildRouterInfo(routers);
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String command = "/ip/firewall/address-list/print";
				result = apiConnection.execute(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> getServerAddressListByContrato(Long idServer, String nroContrato) throws MikrotikApiException {
		List<?> result = null;
		try {
			Routers routers = routersRepository.getReferenceById(idServer);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = buildRouterInfo(routers);
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String command = String.format("/ip/firewall/address-list/print where comment=%s", nroContrato);
				result = apiConnection.execute(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> addAddressList(Long idServer, String ipAddress, String nroContrato, String nombrePlan) throws MikrotikApiException {
		List<?> result = null;
		try {
			Routers routers = routersRepository.getReferenceById(idServer);
			if (validateMicrotik.validarRouter(routers.getIp(), routers.getPuerto().intValue())) {
				RoutersDto routersDto = buildRouterInfo(routers);
				ApiConnection apiConnection = connectMicrotik.apiConnection(routersDto);
				String command = String.format(
		                "/ip/firewall/address-list/add list=%s address=%s comment=%s",
		                nombrePlan, ipAddress, nroContrato
		            );
				result = apiConnection.execute(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
}
