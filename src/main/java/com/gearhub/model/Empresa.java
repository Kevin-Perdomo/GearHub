package com.gearhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank(message = "CNPJ/CPF é obrigatório")
    @Size(max = 18, message = "CNPJ/CPF deve ter no máximo 18 caracteres")
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sede> sedes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}
