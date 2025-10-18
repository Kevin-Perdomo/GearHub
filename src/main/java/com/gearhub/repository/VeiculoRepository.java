package com.gearhub.repository;

import com.gearhub.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findBySedeId(Long sedeId);
    List<Veiculo> findByPlaca(String placa);
}
