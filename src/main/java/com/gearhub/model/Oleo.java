package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "GH_OLEOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oleo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "Data da troca é obrigatória")
    @Column(name = "DATA_TROCA", nullable = false)
    private LocalDate dataTroca;

    @Positive(message = "Quilometragem deve ser positiva")
    @Column(name = "QUILOMETRAGEM")
    private Integer quilometragem;

    @Column(name = "TIPO_OLEO", length = 100)
    private String tipoOleo; // Ex: "5W30 Sintético", "10W40 Semissintético"

    @Positive(message = "Quantidade de litros deve ser positiva")
    @Column(name = "QUANTIDADE_LITROS")
    private Double quantidadeLitros;

    @Column(name = "TROCOU_FILTRO")
    private Boolean trocouFiltro;

    @Lob
    @Column(name = "FOTO", columnDefinition = "LONGBLOB")
    private byte[] foto;

    @Column(name = "NOME_ARQUIVO_FOTO", length = 255)
    private String nomeArquivoFoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEICULO_ID", nullable = false)
    @ToString.Exclude
    private Veiculo veiculo;
}
