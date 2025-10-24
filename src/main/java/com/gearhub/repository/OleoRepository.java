package com.gearhub.repository;

import com.gearhub.model.Oleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OleoRepository extends JpaRepository<Oleo, Long> {
    List<Oleo> findByVeiculoId(Long veiculoId);
}
