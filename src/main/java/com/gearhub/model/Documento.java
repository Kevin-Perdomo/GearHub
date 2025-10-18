package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "documentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 100, message = "Tipo deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String tipo;

    @NotBlank(message = "Caminho do arquivo é obrigatório")
    @Column(name = "arquivo_path", nullable = false)
    private String arquivoPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_upload", nullable = false, updatable = false)
    private LocalDateTime dataUpload;

    @PrePersist
    protected void onCreate() {
        dataUpload = LocalDateTime.now();
    }
}
