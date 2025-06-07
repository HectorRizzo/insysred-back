package com.insysred.isp.util;

import com.insysred.isp.entities.Routers;
import com.insysred.isp.repository.RouterRepository;
import com.insysred.isp.service.impl.HorarioVisitaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetMicrotik {
  private static final Logger log = LoggerFactory.getLogger(HorarioVisitaServiceImpl.class.getName());
  @Autowired
  private RouterRepository routerRepository;

  public Routers obtenerMicrotik(String ipServer) {
    Routers routers = new Routers();
    try {
     Optional<Routers> existRouter = routerRepository.getByIp(ipServer);
     if(existRouter.isPresent()){
       routers = existRouter.get();
     }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return routers;
  }

}
