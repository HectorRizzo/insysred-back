package com.insysred.isp.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.insysred.isp.dto.MktServidorDto;
import com.insysred.isp.service.declare.MktServidorService;
import com.insysred.isp.service.impl.MikroTikServiceImpl;

import me.legrange.mikrotik.MikrotikApiException;

@RestController
@RequestMapping("/api/server") 
public class MikroTikApiRest { 

	@Autowired
    private MktServidorService service;
	
	@Autowired
	MikroTikServiceImpl mktServ;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json") 
    public ResponseEntity<MktServidorDto> create(@RequestBody MktServidorDto mktServidorDto) {
    	MktServidorDto saved = service.save(mktServidorDto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MktServidorDto> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); 
    }

    @GetMapping(value = "/getAll", produces = "application/json") 
    public ResponseEntity<Page<MktServidorDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }
    
    @GetMapping(value = "/servidores", produces = "application/json") 
    public ResponseEntity<List<MktServidorDto>> servidores() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MktServidorDto> update(@PathVariable Long id, @RequestBody MktServidorDto MktServidorDto) {
        MktServidorDto.setServerid(id);
        MktServidorDto updated = service.save(MktServidorDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
//    @GetMapping(value = "/routerInfo", produces = "application/json") 
//    public String routerInfo() throws MikrotikApiException {
//    	String result = mktServ.getRouterInfo();
//		return result; 
//    } 
    
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
    

}
