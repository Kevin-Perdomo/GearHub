package com.gearhub.repository;

import com.gearhub.model.HistoricoPneu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoPneuRepository extends JpaRepository<HistoricoPneu, Long> {
    List<HistoricoPneu> findByVeiculoIdOrderByDataInstalacaoDesc(Long veiculoId);
    List<HistoricoPneu> findByVeiculoIdAndPosicao(Long veiculoId, String posicao);
}
