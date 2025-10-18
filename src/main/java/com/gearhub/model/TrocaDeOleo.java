package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "trocas_oleo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrocaDeOleo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data da troca é obrigatória")
    @Column(name = "data_troca", nullable = false)
    private LocalDate dataTroca;

    @Positive(message = "Quilometragem deve ser positiva")
    @Column(name = "quilometragem")
    private Integer quilometragem;

    @Column(name = "tipo_oleo", length = 100)
    private String tipoOleo;

    @Positive(message = "Quantidade de litros deve ser positiva")
    @Column(name = "quantidade_litros")
    private Double quantidadeLitros;

    @Column(name = "trocou_filtro")
    private Boolean trocouFiltro;

    @Column(name = "url_foto")
    private String urlFoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;
}
