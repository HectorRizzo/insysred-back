package com.insysred.isp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.insysred.isp.entities.InventarioEquipos;
import com.insysred.isp.entities.MarcaEquipos;
import jakarta.validation.Valid;
import lombok.Data;
import java.io.Serializable;
import java.time.Instant;

@Data
public class InventarioEquipoDTO implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @Valid
    private String facturaCompra;
    private Instant fechaCompra;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ip;
    @Valid
    private String macAddress;
    @Valid
    private Long idMarcaEquipo;
    private String modoOperacion;
    private Long idEstado;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String estado;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String iconoEstado;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MarcaEquipos marcaEquipos;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean activo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String colorEstado;

    public InventarioEquipoDTO() {
    }


    // Add this constructor
    public InventarioEquipoDTO(InventarioEquipos inventarioEquipos) {
        this.id = inventarioEquipos.getId();
        this.facturaCompra = inventarioEquipos.getFacturaCompra();
        this.fechaCompra = inventarioEquipos.getFechaCompra();
        this.ip = inventarioEquipos.getIp();
        this.macAddress = inventarioEquipos.getMacAddress();
        this.idMarcaEquipo = inventarioEquipos.getMarcaEquipos()!=null ? inventarioEquipos.getMarcaEquipos().getId() : null;
        this.marcaEquipos = inventarioEquipos.getMarcaEquipos();
        this.modoOperacion = inventarioEquipos.getModoOperacion();
        this.idEstado = inventarioEquipos.getEstadoEquipos() != null ? inventarioEquipos.getEstadoEquipos().getId() : null;
        this.estado = inventarioEquipos.getEstadoEquipos() != null ? inventarioEquipos.getEstadoEquipos().getNombre() : null;
        this.iconoEstado = inventarioEquipos.getEstadoEquipos() != null ? inventarioEquipos.getEstadoEquipos().getIcono() : null;
        this.colorEstado = inventarioEquipos.getEstadoEquipos() != null ? inventarioEquipos.getEstadoEquipos().getColor() : null;
        this.activo = inventarioEquipos.getActivo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
