package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "historico_pneus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoPneu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Posição é obrigatória")
    @Column(name = "posicao", length = 50, nullable = false)
    private String posicao;

    @Column(name = "marca", length = 100)
    private String marca;

    @Column(name = "modelo", length = 100)
    private String modelo;

    @Column(name = "medida", length = 50)
    private String medida;

    @Column(name = "data_instalacao")
    private LocalDate dataInstalacao;

    @Positive(message = "Quilometragem de instalação deve ser positiva")
    @Column(name = "km_instalacao")
    private Integer kmInstalacao;

    @Column(name = "url_foto")
    private String urlFoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;
}
