package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "GH_BATERIAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "Marca é obrigatória")
    @Column(name = "MARCA", length = 100, nullable = false)
    private String marca;

    @Column(name = "MODELO", length = 100)
    private String modelo;

    @Column(name = "VOLTAGEM", length = 20)
    private String voltagem;

    @Column(name = "CAPACIDADE_AH", length = 20)
    private String capacidadeAh;

    @Column(name = "DATA_INSTALACAO")
    private LocalDate dataInstalacao;

    @Positive(message = "Quilometragem de instalação deve ser positiva")
    @Column(name = "KM_INSTALACAO")
    private Integer kmInstalacao;

    @Column(name = "FIM_GARANTIA")
    private LocalDate fimGarantia;

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
