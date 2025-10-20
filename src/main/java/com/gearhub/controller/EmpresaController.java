package com.gearhub.controller;

import com.gearhub.model.Empresa;
import com.gearhub.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaRepository empresaRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empresas", empresaRepository.findAll());
        return "empresas/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
        model.addAttribute("empresa", empresa);
        return "empresas/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
        model.addAttribute("empresa", empresa);
        return "empresas/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Empresa empresa, 
                        BindingResult result, 
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "empresas/form";
        }
        empresaRepository.save(empresa);
        redirectAttributes.addFlashAttribute("mensagem", "Empresa salva com sucesso!");
        return "redirect:/empresas";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            empresaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Empresa excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir empresa. Verifique se não há sedes associadas.");
        }
        return "redirect:/empresas";
    }
}
