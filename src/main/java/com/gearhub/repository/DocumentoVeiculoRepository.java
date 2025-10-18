package com.gearhub.repository;

import com.gearhub.model.DocumentoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoVeiculoRepository extends JpaRepository<DocumentoVeiculo, Long> {
    List<DocumentoVeiculo> findByVeiculoIdOrderByAnoReferenciaDesc(Long veiculoId);
    List<DocumentoVeiculo> findByVeiculoIdAndTipoDocumento(Long veiculoId, String tipoDocumento);
}
