package com.gearhub.repository;

import com.gearhub.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findBySedeId(Long sedeId);
    List<Veiculo> findByPlaca(String placa);
    
    @Query("SELECT v FROM Veiculo v LEFT JOIN FETCH v.sede LEFT JOIN FETCH v.documentos WHERE v.id = :id")
    Optional<Veiculo> findByIdWithDetails(@Param("id") Long id);
}
