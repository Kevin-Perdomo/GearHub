package com.gearhub.repository;

import com.gearhub.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByVeiculoId(Long veiculoId);
    List<Documento> findByTipoDocumento(String tipoDocumento);
}
