package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "GH_DOCUMENTOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "Tipo de documento é obrigatório")
    @Size(max = 100, message = "Tipo deve ter no máximo 100 caracteres")
    @Column(name = "TIPO_DOCUMENTO", nullable = false, length = 100)
    private String tipoDocumento; // Ex: "IPVA", "CRLV", "Seguro", "Manual", "Nota Fiscal"

    @Column(name = "ANO_REFERENCIA")
    private Integer anoReferencia; // Para documentos anuais como IPVA

    @Size(max = 50, message = "Status deve ter no máximo 50 caracteres")
    @Column(name = "STATUS", length = 50)
    private String status; // Ex: "Pago", "Pendente", "Vencido"

    @Column(name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    @Column(name = "DATA_UPLOAD", nullable = false, updatable = false)
    private LocalDateTime dataUpload;

    @Lob
    @Column(name = "ARQUIVO_PDF", columnDefinition = "LONGBLOB")
    private byte[] arquivoPdf;

    @Column(name = "NOME_ARQUIVO", length = 255)
    private String nomeArquivo;

    @Column(name = "DESCRICAO", columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEICULO_ID", nullable = false)
    private Veiculo veiculo;

    @PrePersist
    protected void onCreate() {
        dataUpload = LocalDateTime.now();
    }
}
