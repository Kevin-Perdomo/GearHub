package com.gearhub.controller;

import com.gearhub.model.Veiculo;
import com.gearhub.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public String listarVeiculos(Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "lista-veiculos";
    }

    @GetMapping("/{id}")
    public String detalhesVeiculo(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        
        model.addAttribute("veiculo", veiculo);
        return "detalhes-veiculo";
    }
}
