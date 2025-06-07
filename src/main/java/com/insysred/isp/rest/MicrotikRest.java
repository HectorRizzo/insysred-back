package com.insysred.isp.rest;

import com.insysred.isp.dto.IdMicrotikParamDto;
import com.insysred.isp.dto.microtik.VlanDto;
import com.insysred.isp.service.declare.MikroTikService;
import com.insysred.isp.util.Encriptar;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MicrotikRest {

  @Autowired
  private MikroTikService microtikService;

  @Autowired
  private Encriptar encriptar;

  @GetMapping("/microtik")
  public ResponseEntity<List<?>> getAllConfig() {
    try {
      System.out.println(microtikService.getRouterInfo());
      return new ResponseEntity<>(null, HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping(value = "/microtik/interface/{id_router}", produces = "application/json")
  public ResponseEntity<?> getInterfaces(@PathVariable Long id_router) {
    try {
      return new ResponseEntity<>(microtikService.getInterfaces(id_router), HttpStatus.OK);
    } catch (MikrotikApiException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/microtik/arp",  produces = "application/json")
  public ResponseEntity<List<Map<String, String>>> getIpArp() {
    try {
      List<Map<String, String>> interfaces = microtikService.getARP();
      return new ResponseEntity<>(interfaces, HttpStatus.OK);
    } catch (MikrotikApiException e) {
      // Manejar la excepción adecuadamente, por ejemplo, devolver un error 500
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/microtik/firewall/queue",  produces = "application/json")
  public ResponseEntity<List<Map<String, String>>> setRuleFirewall() {
    try {
      System.out.println("uno");
      List<Map<String, String>> interfaces = microtikService.setQueue();

      return new ResponseEntity<>(interfaces, HttpStatus.OK);
    } catch (MikrotikApiException e) {
      // Manejar la excepción adecuadamente, por ejemplo, devolver un error 500
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/microtik/firewall/upqueue",  produces = "application/json")
  public ResponseEntity<List<Map<String, String>>> updateQueueRule() throws MikrotikApiException {
    List<Map<String, String>> interfaces = microtikService.updateQueue();
    return new ResponseEntity<>(interfaces, HttpStatus.OK);
  }

  @PostMapping(value = "/microtik/get_interfaz",  produces = "application/json")
  public ResponseEntity<?> getInterfaces(@RequestBody IdMicrotikParamDto idMikro) {
    try {
      List<Map<String, String>> interfaces = microtikService.getInterfacesByRouter(idMikro);
      return new ResponseEntity<>(interfaces, HttpStatus.OK);
    } catch (MikrotikApiException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "/microtik/add_vlan",  produces = "application/json")
  public ResponseEntity<?> addVlan(@RequestBody VlanDto vlanDto) {
    try {
      List<Map<String, String>> interfaces = microtikService.addVlan(vlanDto);
      return new ResponseEntity<>(interfaces, HttpStatus.OK);
    } catch (MikrotikApiException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "/microtik/add_mangle",  produces = "application/json")
  public ResponseEntity<?> addMangle(@RequestHeader("Idsucursal") Long idSucursal) {
    try {
      List<Map<String, String>> interfaces = microtikService.addMangle(idSucursal);
      return new ResponseEntity<>(interfaces, HttpStatus.OK);
    } catch (MikrotikApiException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/microtik/view_mangle/{id_router}",  produces = "application/json")
  public ResponseEntity<?> viewMangle(@PathVariable Long id_router) throws MikrotikApiException {
    try {
      return new ResponseEntity<>(microtikService.getMangle(id_router), HttpStatus.OK);
    } catch (MikrotikApiException e) {
      // Manejar la excepción adecuadamente, por ejemplo, devolver un error 500
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/microtik/arp/{id_router}",  produces = "application/json")
  public ResponseEntity<?> getIpArpRouter(@PathVariable Long id_router) {
    try {
      return new ResponseEntity<>(microtikService.getARPRouter(id_router), HttpStatus.OK);
    } catch (MikrotikApiException e) {
      // Manejar la excepción adecuadamente, por ejemplo, devolver un error 500
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/microtik/firewall/view_queue/{id_router}",  produces = "application/json")
  public ResponseEntity<?> viewQueueRule(@PathVariable Long id_router) throws MikrotikApiException {
    try {
      return new ResponseEntity<>(microtikService.getQueues(id_router), HttpStatus.OK);
    } catch (MikrotikApiException e) {
      // Manejar la excepción adecuadamente, por ejemplo, devolver un error 500
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/microtik/firewall/{id_router}",  produces = "application/json")
  public ResponseEntity<?> getFirewall(@PathVariable Long id_router) {
    try {
      return new ResponseEntity<>(microtikService.getFirewall(id_router), HttpStatus.OK);
    } catch (MikrotikApiException e) {
      // Manejar la excepción adecuadamente, por ejemplo, devolver un error 500
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
