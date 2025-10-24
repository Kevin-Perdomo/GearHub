package com.gearhub.repository;

import com.gearhub.model.Bateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BateriaRepository extends JpaRepository<Bateria, Long> {
    List<Bateria> findByVeiculoId(Long veiculoId);
}
