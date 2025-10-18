package com.gearhub.repository;

import com.gearhub.model.HistoricoBateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoBateriaRepository extends JpaRepository<HistoricoBateria, Long> {
    List<HistoricoBateria> findByVeiculoIdOrderByDataInstalacaoDesc(Long veiculoId);
}
