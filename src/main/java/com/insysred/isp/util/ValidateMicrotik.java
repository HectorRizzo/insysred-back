package com.insysred.isp.util;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

@Service
public class ValidateMicrotik {
  public Boolean validarRouter(String ip_router, Integer puerto) {
     int timeoutMillis = 5000; // Tiempo de espera en milisegundos (5 segundos en este ejemplo)
    Boolean activo = false;
    try {
      Socket socket = new Socket();
      socket.connect(new java.net.InetSocketAddress(ip_router, puerto), timeoutMillis);
      //System.out.println("El dispositivo MikroTik en " + ip_router + " está operativo.");
      activo = true;
      socket.close();
    } catch (SocketTimeoutException e) {
      activo = false;
      //System.out.println("El tiempo de espera para conectar al dispositivo MikroTik en " + ip_router + " se agotó.");
    } catch (IOException e) {
      //System.out.println("No se pudo conectar al dispositivo MikroTik en " + ip_router + ".");
      activo = false;
      e.printStackTrace();
    }
    return activo;
  }
}
