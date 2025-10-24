package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GH_AUTONOMIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autonomia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Size(max = 50, message = "Combustível recomendado deve ter no máximo 50 caracteres")
    @Column(name = "COMBUSTIVEL_RECOMENDADO", length = 50)
    private String combustivelRecomendado;

    @Positive(message = "Capacidade do tanque deve ser positiva")
    @Column(name = "CAPACIDADE_TANQUE_LITROS")
    private Double capacidadeTanqueLitros;

    @Positive(message = "Capacidade da reserva deve ser positiva")
    @Column(name = "CAPACIDADE_RESERVA_LITROS")
    private Double capacidadeReservaLitros;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEICULO_ID", nullable = false, unique = true)
    private Veiculo veiculo;
}
