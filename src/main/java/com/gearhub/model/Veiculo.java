package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Modelo é obrigatório")
    @Size(max = 100, message = "Modelo deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String modelo;

    @Size(max = 100, message = "Marca deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String marca;

    @Size(max = 20, message = "Placa deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String placa;

    @Column(name = "ano_modelo")
    private Integer anoModelo;

    @Column(name = "combustivel_recomendado", length = 50)
    private String combustivelRecomendado;

    @Column(name = "capacidade_tanque_litros")
    private Double capacidadeTanqueLitros;

    @Column(name = "capacidade_reserva_litros")
    private Double capacidadeReservaLitros;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Manutencao> manutencoes = new ArrayList<>();

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos = new ArrayList<>();

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrocaDeOleo> trocasDeOleo = new ArrayList<>();

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoBateria> historicoBateria = new ArrayList<>();

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoPneu> historicoPneus = new ArrayList<>();

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentoVeiculo> documentosVeiculo = new ArrayList<>();
}
