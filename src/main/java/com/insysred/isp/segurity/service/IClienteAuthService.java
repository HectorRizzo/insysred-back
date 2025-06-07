package com.insysred.isp.segurity.service;

import com.insysred.isp.dto.ClienteDto;
import com.insysred.isp.segurity.dto.SigninClienteRequest;

public interface IClienteAuthService {

    ClienteDto signinCliente(SigninClienteRequest request);
}
