package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "manutencoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 100, message = "Tipo deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String tipo;

    @Column(name = "data_realizada")
    private LocalDate dataRealizada;

    @Column(name = "data_proxima")
    private LocalDate dataProxima;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;
}
