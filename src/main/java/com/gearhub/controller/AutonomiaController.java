package com.gearhub.controller;

import com.gearhub.model.Autonomia;
import com.gearhub.repository.AutonomiaRepository;
import com.gearhub.repository.VeiculoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/autonomia")
@RequiredArgsConstructor
public class AutonomiaController {

    private final AutonomiaRepository autonomiaRepository;
    private final VeiculoRepository veiculoRepository;

    @GetMapping
    public String listar(@RequestParam(required = false) Long veiculoId, Model model) {
        if (veiculoId != null) {
            // Filtrar por veículo específico
            List<Autonomia> autonomias = autonomiaRepository.findByVeiculoId(veiculoId)
                .map(java.util.Collections::singletonList)
                .orElse(java.util.Collections.emptyList());
            model.addAttribute("autonomias", autonomias);
            model.addAttribute("veiculo", veiculoRepository.findById(veiculoId).orElse(null));
        } else {
            // Listar todas
            model.addAttribute("autonomias", autonomiaRepository.findAll());
        }
        return "autonomia/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("autonomia", new Autonomia());
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "autonomia/form";
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return autonomiaRepository.findById(id)
                .map(autonomia -> {
                    model.addAttribute("autonomia", autonomia);
                    return "autonomia/detalhes";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Autonomia não encontrada");
                    return "redirect:/autonomia";
                });
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return autonomiaRepository.findById(id)
                .map(autonomia -> {
                    model.addAttribute("autonomia", autonomia);
                    model.addAttribute("veiculos", veiculoRepository.findAll());
                    return "autonomia/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Autonomia não encontrada");
                    return "redirect:/autonomia";
                });
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Autonomia autonomia,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("veiculos", veiculoRepository.findAll());
            return "autonomia/form";
        }
        autonomiaRepository.save(autonomia);
        redirectAttributes.addFlashAttribute("mensagem", "Autonomia salva com sucesso!");
        return "redirect:/autonomia";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            autonomiaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Autonomia excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir autonomia.");
        }
        return "redirect:/autonomia";
    }
}
