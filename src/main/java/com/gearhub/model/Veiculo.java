package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GH_VEICULOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "Modelo é obrigatório")
    @Size(max = 100, message = "Modelo deve ter no máximo 100 caracteres")
    @Column(name = "MODELO", nullable = false, length = 100)
    private String modelo;

    @Size(max = 100, message = "Marca deve ter no máximo 100 caracteres")
    @Column(name = "MARCA", length = 100)
    private String marca;

    @Size(max = 20, message = "Placa deve ter no máximo 20 caracteres")
    @Column(name = "PLACA", length = 20)
    private String placa;

    @Column(name = "ANO_MODELO")
    private Integer anoModelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEDE_ID", nullable = false)
    @ToString.Exclude
    private Sede sede;

    @Column(name = "DESCRICAO", columnDefinition = "TEXT")
    private String descricao;

    @Lob
    @Column(name = "FOTO", columnDefinition = "LONGBLOB")
    private byte[] foto;

    @Column(name = "NOME_ARQUIVO_FOTO", length = 255)
    private String nomeArquivoFoto;

    @OneToOne(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Autonomia autonomia;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Documento> documentos = new ArrayList<>();

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Bateria> baterias = new ArrayList<>();

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Pneu> pneus = new ArrayList<>();

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Oleo> oleos = new ArrayList<>();
}
