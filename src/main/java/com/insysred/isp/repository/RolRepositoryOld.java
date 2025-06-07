package com.insysred.isp.repository;

import com.insysred.isp.entities.RolOld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepositoryOld extends JpaRepository<RolOld, Long> {

    @Query(value = "SELECT r FROM RolOld r WHERE r.isActive = true")
    List<RolOld> findAllActivos();
}
