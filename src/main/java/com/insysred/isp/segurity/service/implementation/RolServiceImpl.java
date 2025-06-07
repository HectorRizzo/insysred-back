package com.insysred.isp.segurity.service.implementation;

import com.insysred.isp.segurity.models.Rol;
import com.insysred.isp.segurity.repository.RolRepository;
import com.insysred.isp.segurity.service.IRolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements IRolService {
    private final RolRepository rolRepository;

    @Override
    @Transactional
    public Rol createRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rol> findAllRoles() {
        return rolRepository.findAll();
    }

    @Override
    @Transactional
    public Rol updateRolById(Integer id, Rol rol) {
        Rol rolToUpdate = this.findRolById(id);
        rolToUpdate.setNombre(rol.getNombre());
        return rolRepository.save(rolToUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol findRolById(Integer id) {
        return rolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El rol con id + '" + id + "' no existe"));
    }

    @Override
    @Transactional
    public void deleteRolById(Integer id) {
        Rol rol = this.findRolById(id);
        rolRepository.deleteById(rol.getId());
    }
}
