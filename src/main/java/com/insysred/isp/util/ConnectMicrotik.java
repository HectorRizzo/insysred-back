package com.insysred.isp.util;

import com.insysred.isp.dto.RoutersDto;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.stereotype.Service;

import javax.net.SocketFactory;

@Service
public class ConnectMicrotik {
    public ApiConnection apiConnection(RoutersDto routersDto) throws MikrotikApiException {
        ApiConnection connection = ApiConnection.connect(SocketFactory.getDefault(), routersDto.getIp(), routersDto.getPuerto(), 3000);
        connection.login(routersDto.getUsuario(), routersDto.getPassword());
        return connection;
    }
}
