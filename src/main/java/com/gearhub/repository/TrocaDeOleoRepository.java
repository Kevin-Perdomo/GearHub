package com.gearhub.repository;

import com.gearhub.model.TrocaDeOleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrocaDeOleoRepository extends JpaRepository<TrocaDeOleo, Long> {
    List<TrocaDeOleo> findByVeiculoIdOrderByDataTrocaDesc(Long veiculoId);
}
