package com.insysred.isp.service.declare;

import java.util.List;
import java.util.Map;

import com.insysred.isp.dto.IdMicrotikParamDto;
import com.insysred.isp.dto.MkservervlanDto;
import com.insysred.isp.dto.PlanesDto;
import com.insysred.isp.dto.microtik.VlanDto;

import me.legrange.mikrotik.MikrotikApiException;

public interface MikroTikService {
	List<Map<String, String>> getRouterInfo() throws MikrotikApiException;

	List<?> getInterfaces(Long id_router) throws MikrotikApiException;

	List<?> getInterfaces() throws MikrotikApiException;

	List<Map<String, String>> getARP() throws MikrotikApiException;

	List<?> getFirewall(Long id_router) throws MikrotikApiException;

	List<?> getFirewall() throws MikrotikApiException;

	void agregarServidorIP();

	void crearReglaFirewall(String regla);

	List<Map<String, String>> setQueue() throws MikrotikApiException;

	List<Map<String, String>> updateQueue() throws MikrotikApiException;

	List<?> getQueues(Long id_router) throws MikrotikApiException;

	List<?> getQueues() throws MikrotikApiException;

	// Crear Queue según contrato
	List<Map<String, String>> newQueue(PlanesDto planesDto) throws MikrotikApiException;

	List<Map<String, String>> getInterfacesByRouter(IdMicrotikParamDto idRouter) throws MikrotikApiException;

	List<Map<String, String>> addVlan(VlanDto vlanDto) throws MikrotikApiException;

	List<Map<String, String>> addMangle(Long idSucursal) throws MikrotikApiException;

	List<?> getMangle(Long id_router) throws MikrotikApiException;

	List<?> getMangle() throws MikrotikApiException;

	List<?> getARPRouter(Long id_router) throws MikrotikApiException;
	
	List<?> getServerRules(Long idServer) throws MikrotikApiException;
	
	List<?> addServerRule(Long idServer) throws MikrotikApiException;
	
	List<?> getServerNats(Long idServer) throws MikrotikApiException;
	
	List<?> addServerNat(Long idServer) throws MikrotikApiException;
	
	List<?> getServerVlan(Long idServer) throws MikrotikApiException;
	
	List<?> getServerIpAddress(Long idServer) throws MikrotikApiException;

	List<Map<String, String>> addServerVlan(MkservervlanDto vlanDto) throws MikrotikApiException;
	
	List<?> getServerAddressList(Long idServer) throws MikrotikApiException;
	
	List<?> getServerAddressListByContrato(Long idServer, String nroContrato) throws MikrotikApiException;
	
	List<?> addAddressList(Long idServer, String ipAddress, String nroContrato, String nombrePlan) throws MikrotikApiException;
	
	
}
