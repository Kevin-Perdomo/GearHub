package com.gearhub.controller;

import com.gearhub.model.Veiculo;
import com.gearhub.repository.SedeRepository;
import com.gearhub.repository.VeiculoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;
    private final SedeRepository sedeRepository;

    @GetMapping
    public String listarVeiculos(Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "veiculos/lista";
    }

    @GetMapping("/novo")
    public String novoVeiculo(Model model) {
        model.addAttribute("veiculo", new Veiculo());
        model.addAttribute("sedes", sedeRepository.findAll());
        return "veiculos/form";
    }

    @GetMapping("/{id}")
    public String detalhesVeiculo(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculoRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        
        model.addAttribute("veiculo", veiculo);
        return "veiculos/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editarVeiculo(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado"));
        model.addAttribute("veiculo", veiculo);
        model.addAttribute("sedes", sedeRepository.findAll());
        return "veiculos/form";
    }

    @PostMapping
    public String salvarVeiculo(@Valid @ModelAttribute Veiculo veiculo,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("sedes", sedeRepository.findAll());
            return "veiculos/form";
        }
        veiculoRepository.save(veiculo);
        redirectAttributes.addFlashAttribute("mensagem", "Veículo salvo com sucesso!");
        return "redirect:/veiculos";
    }

    @GetMapping("/{id}/excluir")
    public String excluirVeiculo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            veiculoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Veículo excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir veículo. Verifique se não há documentos associados.");
        }
        return "redirect:/veiculos";
    }
}
