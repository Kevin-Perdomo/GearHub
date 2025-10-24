package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "GH_PNEUS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pneu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "Posição é obrigatória")
    @Column(name = "POSICAO", length = 50, nullable = false)
    private String posicao; // Ex: "Dianteiro Esquerdo", "Traseiro Direito", "Estepe"

    @Column(name = "MARCA", length = 100)
    private String marca;

    @Column(name = "MODELO", length = 100)
    private String modelo;

    @Column(name = "MEDIDA", length = 50)
    private String medida; // Ex: "185/65 R15"

    @Column(name = "DATA_INSTALACAO")
    private LocalDate dataInstalacao;

    @Positive(message = "Quilometragem de instalação deve ser positiva")
    @Column(name = "KM_INSTALACAO")
    private Integer kmInstalacao;

    @Lob
    @Column(name = "FOTO", columnDefinition = "LONGBLOB")
    private byte[] foto;

    @Column(name = "NOME_ARQUIVO_FOTO", length = 255)
    private String nomeArquivoFoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEICULO_ID", nullable = false)
    private Veiculo veiculo;
}
