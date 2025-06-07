package com.insysred.isp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "inventario_equipos")
@Data
public class InventarioEquipos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "factura_compra")
    private String facturaCompra;

    @Column(name = "fecha_compra")
    private Instant fechaCompra;

    @Column(name = "ip")
    private String ip;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "id_marca")
    private Long idMarca;

    @Column(name = "modo_operacion")
    private String modoOperacion;

    @Column(name = "id_estado")
    private Long idEstado;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_marca", referencedColumnName = "id", insertable = false, updatable = false)
    private MarcaEquipos marcaEquipos;

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id", insertable = false, updatable = false)
    private EstadoEquipo estadoEquipos;


}
