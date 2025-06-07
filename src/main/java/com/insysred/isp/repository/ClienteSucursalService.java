package com.insysred.isp.repository;

import com.insysred.isp.dto.SucursalClienteDto;
import com.insysred.isp.dto.UsuarioSucursalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteSucursalService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<SucursalClienteDto> obtenerClienteSucursales(Long clienteId) {
    String query = "SELECT s.id, s.nombre, s.establecimiento, s.punto_emision, s.secuencial, COUNT(DISTINCT c.id) AS cliente\n" +
      ",CASE WHEN COUNT(DISTINCT c.id) = 1 THEN TRUE ELSE FALSE END AS cliente_presente FROM sucursales AS s LEFT JOIN cliente_sucursal AS cs ON s.id = cs.sucursal_id\n" +
      "LEFT JOIN cliente AS c ON c.id = cs.cliente_id AND c.id = ? WHERE s.is_active = true\n" +
      "GROUP BY s.id, s.nombre, s.establecimiento, s.punto_emision, s.secuencial";
    return jdbcTemplate.query(query, new Object[]{clienteId}, new BeanPropertyRowMapper<>(SucursalClienteDto.class));
  }


}
