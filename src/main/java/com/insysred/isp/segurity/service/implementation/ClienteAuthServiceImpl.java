package com.insysred.isp.segurity.service.implementation;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.ClienteDto;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.mapper.ClienteMapper;
import com.insysred.isp.repository.ClienteRepository;
import com.insysred.isp.segurity.dto.SigninClienteRequest;
import com.insysred.isp.segurity.service.IClienteAuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteAuthServiceImpl implements IClienteAuthService {
    
    @Autowired
    private final ClienteRepository clienteRepository;
    @Autowired
    private final ClienteMapper clienteMapper;

	@Override
	public ClienteDto signinCliente(SigninClienteRequest request) {
		Cliente cliente = clienteRepository.getByIdentificacion(request.getTipoDocumento(), request.getIdentificacion());
		if (ObjectUtils.isEmpty(cliente)) {
			throw new IllegalArgumentException("El cliente no existe.");
		}
		return clienteMapper.toDto(cliente);
	}
}
