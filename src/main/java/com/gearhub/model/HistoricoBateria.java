package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "historico_bateria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoBateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Marca é obrigatória")
    @Column(name = "marca", length = 100, nullable = false)
    private String marca;

    @Column(name = "modelo", length = 100)
    private String modelo;

    @Column(name = "voltagem", length = 20)
    private String voltagem;

    @Column(name = "capacidade_ah", length = 20)
    private String capacidadeAh;

    @Column(name = "data_instalacao")
    private LocalDate dataInstalacao;

    @Positive(message = "Quilometragem de instalação deve ser positiva")
    @Column(name = "km_instalacao")
    private Integer kmInstalacao;

    @Column(name = "fim_garantia")
    private LocalDate fimGarantia;

    @Column(name = "url_foto")
    private String urlFoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;
}
