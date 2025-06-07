package com.insysred.isp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Contrato;
import com.insysred.isp.entities.EstadoContrato;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;


public class ClienteContratoDTO {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ClienteContratoDTO.class);
    private Cliente cliente;

    private static Logger logger = Logger.getLogger(ClienteContratoDTO.class.getName());
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long contrato;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EstadoContrato estadoContrato;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ip;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String plan;

    // Add this constructor
    public ClienteContratoDTO(Cliente cliente, Long contrato, EstadoContrato estadoContrato, String ip, String plan) {
        this.cliente = cliente;
        this.contrato = contrato;
        this.estadoContrato = estadoContrato;
        this.ip = ip;
        this.plan = plan;
    }

    public ClienteContratoDTO(){
    }

    public Cliente getCliente() {
        return cliente;
    }

    public EstadoContrato getEstadoContrato() {
        return estadoContrato;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        ClienteContratoDTO.logger = logger;
    }

    public void setContrato(Long contrato) {
        this.contrato = contrato;
    }

    public void setEstadoContrato(EstadoContrato estadoContrato) {
        this.estadoContrato = estadoContrato;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getIp() {
        return ip;
    }

    public String getPlan() {
        return plan;
    }

    public Long getContrato() {
        return contrato;
    }

}
