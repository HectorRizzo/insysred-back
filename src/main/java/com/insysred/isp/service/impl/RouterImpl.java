package com.insysred.isp.service.impl;

import com.insysred.isp.dto.*;
import com.insysred.isp.entities.Routers;
import com.insysred.isp.entities.Sucursales;
import com.insysred.isp.mapper.RouterMapper;
import com.insysred.isp.mapper.SucursalMapper;
import com.insysred.isp.repository.RouterRepository;
import com.insysred.isp.repository.SucursalRepository;
import com.insysred.isp.service.declare.RouterService;
import com.insysred.isp.util.Encriptar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouterImpl implements RouterService {
    @Autowired
    private RouterRepository routerRepository;

    @Autowired
    private RouterMapper routerMapper;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private SucursalMapper sucursalMapper;

    @Autowired
    private Encriptar encriptar;

    @Override
    public List<RoutersDto> getAllRouter() {
        return routerMapper.toDto(routerRepository.findAll());
    }

    @Override
    public RoutersDto saveRouter(RouterNewDto routersDto) {

        RoutersDto routersSave = new RoutersDto();
        try {
            Optional<Routers> optionalRouters = routerRepository.getByIp(routersDto.getIp());
            if(!optionalRouters.isPresent()){
                Routers routers = new Routers();
                routers.setNombre(routersDto.getNombre());
                routers.setUsuario(routersDto.getUsuario());
                routers.setPassword(encriptar.encrypt(routersDto.getPassword()));
                routers.setIp(routersDto.getIp());
                routers.setPuerto(routersDto.getPuerto());
                routers.setGateway(routersDto.getGateway());
                routers.setIsActive(true);
                routersSave = routerMapper.toDto(routerRepository.save(routers));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return routersSave;
    }

    @Override
    public RoutersDto getRouter(Long id) {
        RoutersDto router = new RoutersDto();
        try {
            router = routerMapper.toDto(routerRepository.getById(id));
            //router.setPassword((encriptar.decrypt(router.getPassword())));
            router.setPassword("");
        } catch (Exception e){
            e.printStackTrace();
        }
        return router;
    }

    @Override
    public List<RoutersDto> getRouterSucursal(Long idSucursal) {
        return routerMapper.toDto(routerRepository.getRoutersBySucur(idSucursal));
    }

    @Override
    public RoutersDto cambiarEstadoRouter(CambioEstadoDto cambioEstadoDto) {
        Routers routers = routerRepository.getReferenceById(cambioEstadoDto.getIdComponent());
        routers.setIsActive(cambioEstadoDto.getEstatus());
        return routerMapper.toDto(routerRepository.save(routers));
    }

    @Override
    public RoutersDto asignarSucursal(AsignaSucursalDto asignaSucursalDto) {
        Routers routers = routerRepository.getReferenceById(asignaSucursalDto.getIdRouter());
        Sucursales sucursales = sucursalRepository.getReferenceById(asignaSucursalDto.getIdSucursal());
        routers.setSucursal(sucursales);
        return routerMapper.toDto(routerRepository.save(routers));
    }

    @Override
    public RoutersDto updateRouter(Long id, RouterNewDto routerNewDto) {
       RoutersDto routersDto = new RoutersDto();

        Optional<Routers> optionalRouters = routerRepository.getByIp(routerNewDto.getIp());

        if(optionalRouters.isPresent()){
            Routers routers = routerRepository.getReferenceById(id);
            routers.setNombre(routerNewDto.getNombre());
            routers.setUsuario(routerNewDto.getUsuario());
            routers.setIp(routerNewDto.getIp());
            routers.setPuerto(routerNewDto.getPuerto());
            routers.setGateway(routerNewDto.getGateway());
            routersDto = routerMapper.toDto(routerRepository.save(routers));
        }
        return routersDto;
    }

    @Override
    public RoutersDto resetPassRouter(ResetPasswordDto resetPasswordDto) {
        RoutersDto routersDto = new RoutersDto();
        Optional<Routers> optionalRouters = routerRepository.getByIp(resetPasswordDto.getIp());
        if(optionalRouters.isPresent()){
            Routers routers = optionalRouters.get();
            routers.setPassword(resetPasswordDto.getPassword());
            routersDto = routerMapper.toDto(routerRepository.save(routers));
        }
        return routersDto;
    }
}
