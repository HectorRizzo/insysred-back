package com.insysred.isp.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.CambioEstadoDto;
import com.insysred.isp.dto.MkserveripvlanDto;
import com.insysred.isp.dto.MkservervlanDto;
import com.insysred.isp.dto.MkservervlanintrfcDto;
import com.insysred.isp.dto.ResponseDTO;
import com.insysred.isp.entities.Mkserveripvlan;
import com.insysred.isp.entities.Mkservervlan;
import com.insysred.isp.enums.EstadoIP;
import com.insysred.isp.mapper.MkservervlanMapper;
import com.insysred.isp.repository.MkservervlanRepository;
import com.insysred.isp.repository.RouterRepository;
import com.insysred.isp.service.declare.JasperReportService;
import com.insysred.isp.service.declare.MikroTikService;
import com.insysred.isp.service.declare.MkserveripvlanService;
import com.insysred.isp.service.declare.MkservervlanService;
import com.insysred.isp.util.ConnectMicrotik;
import com.insysred.isp.util.SubnetCalculator;

@Service
public class MkservervlanServiceImpl implements MkservervlanService {

	@Autowired
	private MkservervlanRepository mktVlanRepository;

	@Autowired
	private MkserveripvlanService ipVlanService;
	
	@Autowired
	private MkservervlanMapper mktVlanMapper; 

	@Autowired
	private ConnectMicrotik connectMicrotik;
		
	@Autowired
	private MikroTikService mikroTikService;
	
	@Autowired
	private RouterRepository routersRepository;

	@Override
    public MkservervlanDto save(MkservervlanDto mktServidorDto) {
		Mkservervlan mktServidor = mktVlanMapper.toEntity(mktServidorDto);
		Mkservervlan savedEntity = mktVlanRepository.save(mktServidor);
        return mktVlanMapper.toDto(savedEntity);
    }

    @Override
    public Optional<MkservervlanDto> findById(Long id) {
        Optional<Mkservervlan> Mkservervlan = mktVlanRepository.findById(id);
        return Mkservervlan.map(mktVlanMapper::toDto);
    }

    @Override
    public Page<MkservervlanDto> findAll(Pageable pageable) {
        Page<Mkservervlan> page = mktVlanRepository.findAll(pageable);
        return page.map(mktVlanMapper::toDto);
    }

    @Override
    public void deleteById(Long id) {
    	mktVlanRepository.deleteById(id);
    }

	@Override 
	public List<MkservervlanDto> findAll() {
		 return mktVlanMapper.toDto(mktVlanRepository.findAll());
	}

	@Override
	public Page<Mkservervlan> obtenerVlans(PageRequest paginaRequest, Long idRouter, String filtro, Boolean estado) {
		return mktVlanRepository.findByIdRouterAndStatus(idRouter, estado, paginaRequest);
	}

	@Override
	public MkservervlanDto actualizarVlan(Long id, MkservervlanDto vlanNewDto) {
		Mkservervlan vlan = mktVlanRepository.findById(id).get();
		vlan.setComentario(vlanNewDto.getComentario());
		vlan.setIdPadre(vlanNewDto.getIdPadre());
		vlan.setInterfaz(vlanNewDto.getInterfaz());
		vlan.setMascara(vlanNewDto.getMascara());
		vlan.setNombre(vlanNewDto.getNombre());
		return mktVlanMapper.toDto(mktVlanRepository.save(vlan));
	}

	@Override
	public MkservervlanDto cmbEstadoVlan(CambioEstadoDto cambioEstadoDto) {
		Mkservervlan vlan = mktVlanRepository.findById(cambioEstadoDto.getIdComponent()).get();
		vlan.setEsActivo(cambioEstadoDto.getEstatus());
		return mktVlanMapper.toDto(mktVlanRepository.save(vlan));
	}

	@Override
	public MkservervlanDto findByName(Long idRouter, String nombre) {
		Mkservervlan vlan = mktVlanRepository.findByName(idRouter, nombre);
		return mktVlanMapper.toDto(vlan);
	}
	
	@Override
	public List<MkservervlanDto> findByIp(Long idRouter, String ip) {
		List<Mkservervlan> vlan = mktVlanRepository.findByIp(idRouter, ip);
		return mktVlanMapper.toDto(vlan);
	}
	
	public boolean getMkVlans(Long idRouter) {
		boolean result = false;
		if(idRouter != null) {
			try {
				List<?> intrfcLst = mikroTikService.getServerIpAddress(idRouter);
				List<MkservervlanintrfcDto> lstIntrf = new ArrayList<MkservervlanintrfcDto>();
				for (Object itemData : intrfcLst) {
					MkservervlanintrfcDto intrfcItem = new MkservervlanintrfcDto();
					Map<String, String> itemIntrfc = (Map<String, String>) itemData;
					intrfcItem.setInterfaceActualInterface((String) itemIntrfc.get("actual-interface"));
					intrfcItem.setInterfaceAddress((String) itemIntrfc.get("address"));
					intrfcItem.setInterfaceComment((String) itemIntrfc.get("comment"));
					intrfcItem.setInterfaceInterface((String) itemIntrfc.get("interface"));
					intrfcItem.setInterfaceNetwork((String) itemIntrfc.get("network")); 
					lstIntrf.add(intrfcItem);
				}
				
				List<?> vlanLst = mikroTikService.getServerVlan(idRouter);
				for (Object itemData : vlanLst) {
					MkservervlanDto vlanNewDto = null;
					Map<String, String> itemPam = (Map<String, String>) itemData;
					String vlanName = (String) (String) itemPam.get("name");
					vlanNewDto = findByName(idRouter, vlanName);
					if (vlanNewDto == null) {
						vlanNewDto = new MkservervlanDto();
						vlanNewDto.setComentario((String) itemPam.get("mtu"));
						vlanNewDto.setEsActivo(Boolean.valueOf(itemPam.get("running")));
						vlanNewDto.setIdRouter(idRouter);
						vlanNewDto.setInterfaz((String) itemPam.get("interface"));
						vlanNewDto.setMacAddress((String) itemPam.get("mac-address"));
						vlanNewDto.setMktVlanId((String) itemPam.get(".id"));
						vlanNewDto.setNombre(vlanName);
						MkservervlanintrfcDto intTmp = lstIntrf.stream().filter(item -> item.getInterfaceInterface().equals(vlanName)).findFirst().orElse(null);
						if(intTmp != null) {
							String[] netSplit = intTmp.getInterfaceAddress().split("/");
							vlanNewDto.setIp(netSplit[0]);
							vlanNewDto.setMascara(SubnetCalculator.calcMask(Integer.valueOf(netSplit[1])));
							vlanNewDto.setComentario(intTmp.getInterfaceComment());
							vlanNewDto.setMktVlanNetwork(intTmp.getInterfaceNetwork());
						}
						if(SubnetCalculator.isValidIp(vlanNewDto.getIp())) {
							buildIpList(save(vlanNewDto));					
						}
					}
				}
				result = true;
			} catch (Exception e) {
				result = false;
			}
		}
		return result;
	} 
	
	public boolean buildIpList(MkservervlanDto vlanNewDto) throws UnknownHostException {
		boolean result = false;
		if(vlanNewDto != null && vlanNewDto.getIp() != null && vlanNewDto.getMascara() != null && SubnetCalculator.isValidIp(vlanNewDto.getIp()) && SubnetCalculator.isValidIp(vlanNewDto.getMascara())) {
			List<String> ipAddresses = SubnetCalculator.calculateIPsInSubnet(vlanNewDto.getIp(), vlanNewDto.getMascara());
			int ipCounter = 1;
	        for (String ipAddress : ipAddresses) {
	        	if(ipCounter > 1 && ipCounter < ipAddresses.size() && !ipAddress.equals(vlanNewDto.getIp())) {
	        		MkserveripvlanDto ipNew = new MkserveripvlanDto();
	            	ipNew.setEsActivo(true);
	            	ipNew.setEstado(EstadoIP.LIBRE.name());
	            	ipNew.setIdRouter(vlanNewDto.getIdRouter());
	            	ipNew.setIdvlan(vlanNewDto.getId());
	            	ipNew.setIp(ipAddress);
	            	ipVlanService.save(ipNew); 
	        	}
	        	ipCounter++; 
	        } 
		}
		return result;
	}
	
	public ResponseDTO validateNewVlan(MkservervlanDto newDto){
		if(newDto != null) {
			if(routersRepository.findById(newDto.getIdRouter()).isPresent()) {
				if(newDto.getNombre() == null || (newDto.getNombre() != null && newDto.getNombre().isEmpty())) {
					return new ResponseDTO("El nombre de la VLAN no debe ser nulo o vacío ", 204, null);
				}
					
				if(findByName(newDto.getIdRouter(), newDto.getNombre()) != null) {
					return new ResponseDTO("El nombre de la VLAN ya se encuentra registrado en este servidor", 204, null);	
				} 
				
				if(newDto.getInterfaz() == null || (newDto.getInterfaz() != null && newDto.getInterfaz().isEmpty()) ){
					return new ResponseDTO("La interfaz debe ser válidad ", 204, null);	
				}
				
				if(!SubnetCalculator.isValidIp(newDto.getIp())) {
					return new ResponseDTO("La IP debe ser válidad ", 204, null);	
				}
				
				if(!SubnetCalculator.isValidIp(newDto.getMascara())) {
					return new ResponseDTO("La MASCARA debe ser válidad ", 204, null);	
				}	
				 
				if(findByIp(newDto.getIdRouter(), newDto.getIp()).size() > 0) {
					return new ResponseDTO("La IP de la VLAN ya se encuentra registrado en este servidor", 204, null);
				}
				
				return new ResponseDTO("DATOS VALIDOS ", 200, null);
			}else {
				return new ResponseDTO("El servidor/router no existe ", 204, null);
			}
		}else {
			return new ResponseDTO("Los datos recibidos son nulos ", 204, null);
		}
	}
}
