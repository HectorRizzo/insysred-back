package com.insysred.isp.segurity.service;

import com.insysred.isp.segurity.models.Rol;

import java.util.List;

public interface IRolService {
    Rol createRol(Rol rol);
    List<Rol> findAllRoles();

    Rol updateRolById(Integer id, Rol rol);

    Rol findRolById(Integer id);
    void deleteRolById(Integer id);
}
