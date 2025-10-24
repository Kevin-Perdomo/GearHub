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
@Table(name = "GH_SEDES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    @Column(name = "NOME", nullable = false, length = 150)
    private String nome;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    @Column(name = "ENDERECO", length = 255)
    private String endereco;

    @Lob
    @Column(name = "FOTO", columnDefinition = "LONGBLOB")
    private byte[] foto;

    @Column(name = "NOME_ARQUIVO_FOTO", length = 255)
    private String nomeArquivoFoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPRESA_ID", nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Veiculo> veiculos = new ArrayList<>();
}
