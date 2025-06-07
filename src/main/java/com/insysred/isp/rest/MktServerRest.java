package com.insysred.isp.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.insysred.isp.dto.MktServidorDto;
import com.insysred.isp.service.declare.MktServidorService;
import com.insysred.isp.service.impl.MikroTikServiceImpl;

import me.legrange.mikrotik.MikrotikApiException;

@RestController
@RequestMapping("/api/mikrotik")
public class MktServerRest {

	@Autowired
	private MktServidorService service;

	@Autowired
	MikroTikServiceImpl mktServ;

	@GetMapping(value = "/routerInfo", produces = "application/json")
	public List<Map<String, String>> routerInfo() throws MikrotikApiException {
		List<Map<String, String>> result = mktServ.getRouterInfo();
		return result;
	}

	@GetMapping(value = "/arpInfo", produces = "application/json")
	public List<Map<String, String>> routerArpInfo() throws MikrotikApiException {
		List<Map<String, String>> result = mktServ.getARP();
		return result;
	}

	@GetMapping(value = "/getMangle", produces = "application/json")
	public List<?> getMangle() throws MikrotikApiException {
		List<?> result = mktServ.getMangle();
		return result;
	}

	@GetMapping(value = "/getInterfaces", produces = "application/json")
	public List<?> getInterfaces() throws MikrotikApiException {
		List<?> result = mktServ.getInterfaces();
		return result;
	}

	@GetMapping(value = "/getQueues", produces = "application/json")
	public List<?> getQueues() throws MikrotikApiException {
		List<?> result = mktServ.getQueues();
		return result;
	}

	@GetMapping(value = "/getFirewall", produces = "application/json")
	public List<?> getFirewall() throws MikrotikApiException {
		List<?> result = mktServ.getFirewall();
		return result;
	}

	@GetMapping(value = "/getInterfacesById/{id_router}", produces = "application/json")
	public ResponseEntity<?> getInterfacesById(@PathVariable Long id_router) {
		try {
			System.out.println("Detalles del router con ID " + id_router.toString() + ":");
			return new ResponseEntity<>(mktServ.getInterfaces(id_router), HttpStatus.OK);
		} catch (MikrotikApiException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getServerRules/{idServer}", produces = "application/json")
	public List<?> getServerRules(@PathVariable Long idServer) throws MikrotikApiException {
		List<?> result = mktServ.getServerRules(idServer);
		return result;
	}
	
	@GetMapping(value = "/getServerNats/{idServer}", produces = "application/json")
	public List<?> getServerNats(@PathVariable Long idServer) throws MikrotikApiException {
		List<?> result = mktServ.getServerNats(idServer);
		return result;
	}
	
	@GetMapping(value = "/getServerVlans/{idServer}", produces = "application/json")
	public List<?> getServerVlans(@PathVariable Long idServer) throws MikrotikApiException {
		List<?> result = mktServ.getServerVlan(idServer);
		return result;
	}

	@GetMapping(value = "/getServerIpAddress/{idServer}", produces = "application/json")
	public List<?> getServerIpAddress(@PathVariable Long idServer) throws MikrotikApiException {
		List<?> result = mktServ.getServerIpAddress(idServer);
		return result;
	}
	
	@GetMapping(value = "/getServerAddressList/{idServer}", produces = "application/json")
	public List<?> getServerAddressList(@PathVariable Long idServer) throws MikrotikApiException {
		List<?> result = mktServ.getServerAddressList(idServer);
		return result;
	}
	
	@GetMapping(value = "/getServerAddressListByContrato/{idServer}", produces = "application/json")
	public List<?> getServerAddressListByContrato(@PathVariable Long idServer, @RequestParam(defaultValue = "") String nroContrato) throws MikrotikApiException {
		List<?> result = mktServ.getServerAddressListByContrato(idServer, nroContrato);
		return result;
	}
	
	@PostMapping(value = "/addServerAddressList/{idServer}", produces = "application/json")
	public List<?> addServerAddressList(
			@PathVariable Long idServer, 
			@RequestParam(defaultValue = "") String ipAddress,
			@RequestParam(defaultValue = "") String nroContrato,
			@RequestParam(defaultValue = "activos") String nombrePlan) throws MikrotikApiException {
		List<?> result = mktServ.addAddressList(idServer, ipAddress, nroContrato, nombrePlan);
		return result;
	}
	
}
