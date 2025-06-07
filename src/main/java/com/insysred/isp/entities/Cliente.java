package com.insysred.isp.entities;

import com.insysred.isp.enums.Sexo;
import com.insysred.isp.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_identificacion", length = 9)
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(name = "identificacion", length = 13)
    private String identificacion;

    @Column(name = "sexo", length = 10)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column(name = "fecha_nacimiento")
    private Instant fechaNace;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Column(name = "nombres", length = 100)
    private String nombres;

    @Column(name = "razon_social", length = 200)
    private String razonSocial;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telf_fijo", length = 9)
    private String telfFijo;

    @Column(name = "telf_celular", length = 10)
    private String telfCelular;

    @Column(name = "file_identificacion")
    private String fileIdentificacion;

    @Column(name = "file_planilla")
    private String filePlanilla;

    @Column(name = "ubicacion", length = 250)
    private String ubicacion;

    @Column(name = "referencia_ubicacion", length = 250)
    private String referenciaUbicacion;

    @Column(name = "longitud", length = 100)
    private String longitud;

    @Column(name = "latitud", length = 100)
    private String latitud;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cliente_sucursal",
            joinColumns = @JoinColumn(name = "cliente_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sucursal_id", referencedColumnName = "id")
    )
    private List<Sucursales> sucursales;

    @ManyToOne
    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
    private Provincia provincia;

    @ManyToOne
    @JoinColumn(name = "canton_id", referencedColumnName = "id")
    private Canton canton;

    public String toString() {
        return "Cliente(id=" + this.getId() + ", tipoDocumento=" + this.getTipoDocumento() + ", identificacion=" + this.getIdentificacion() + ", sexo=" + this.getSexo() + ", fechaNace=" + this.getFechaNace() + ", apellidos=" + this.getApellidos() + ", nombres=" + this.getNombres() + ", razonSocial=" + this.getRazonSocial() + ", email=" + this.getEmail() + ", telfFijo=" + this.getTelfFijo() + ", telfCelular=" + this.getTelfCelular() + ", fileIdentificacion=" + this.getFileIdentificacion() + ", filePlanilla=" + this.getFilePlanilla() + ", ubicacion=" + this.getUbicacion() + ", referenciaUbicacion=" + this.getReferenciaUbicacion() + ", longitud=" + this.getLongitud() + ", latitud=" + this.getLatitud() + ", sucursales=" + this.getSucursales() + ", provincia=" + this.getProvincia() + ", canton=" + this.getCanton() + ")";
    }

}
