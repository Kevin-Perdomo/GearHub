package com.gearhub.repository;

import com.gearhub.model.Pneu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PneuRepository extends JpaRepository<Pneu, Long> {
    List<Pneu> findByVeiculoId(Long veiculoId);
}
