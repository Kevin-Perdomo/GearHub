package com.gearhub.repository;

import com.gearhub.model.Autonomia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutonomiaRepository extends JpaRepository<Autonomia, Long> {
    Optional<Autonomia> findByVeiculoId(Long veiculoId);
}
