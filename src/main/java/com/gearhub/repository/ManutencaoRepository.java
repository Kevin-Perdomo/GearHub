package com.gearhub.repository;

import com.gearhub.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    List<Manutencao> findByVeiculoId(Long veiculoId);
}
