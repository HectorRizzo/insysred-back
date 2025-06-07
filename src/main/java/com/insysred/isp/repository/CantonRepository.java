package com.insysred.isp.repository;

import com.insysred.isp.entities.Canton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CantonRepository extends JpaRepository<Canton, Long> {

    @Query(value = "select c from Canton c where c.provincia.id =?1")
    List<Canton> getCantonByProvinciaId(Long idProvincia);
}
