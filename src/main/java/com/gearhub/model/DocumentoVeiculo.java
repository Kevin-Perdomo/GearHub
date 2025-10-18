package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "documentos_veiculo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tipo de documento é obrigatório")
    @Column(name = "tipo_documento", length = 100, nullable = false)
    private String tipoDocumento;

    @Column(name = "ano_referencia")
    private Integer anoReferencia;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "url_arquivo")
    private String urlArquivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;
}
