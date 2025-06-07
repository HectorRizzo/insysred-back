package com.insysred.isp.repository;

import com.insysred.isp.entities.Referencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReferenciaRepository extends JpaRepository<Referencia, Long>, JpaSpecificationExecutor<Referencia> {
    @Query(value = "select re from Referencia re where re.cliente.id =?1")
    Optional<Referencia> getByCliente(Long id);

    @Query(value = "select re from Referencia re where re.id =?1")
    Referencia getById(Long id);

}
