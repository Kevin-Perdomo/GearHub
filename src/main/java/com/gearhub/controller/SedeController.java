package com.gearhub.controller;

import com.gearhub.model.Sede;
import com.gearhub.repository.EmpresaRepository;
import com.gearhub.repository.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sedes")
@RequiredArgsConstructor
public class SedeController {

    private final SedeRepository sedeRepository;
    private final EmpresaRepository empresaRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("sedes", sedeRepository.findAll());
        return "sedes/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("sede", new Sede());
        model.addAttribute("empresas", empresaRepository.findAll());
        return "sedes/form";
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sede não encontrada"));
        model.addAttribute("sede", sede);
        return "sedes/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sede não encontrada"));
        model.addAttribute("sede", sede);
        model.addAttribute("empresas", empresaRepository.findAll());
        return "sedes/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Sede sede, 
                        BindingResult result, 
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("empresas", empresaRepository.findAll());
            return "sedes/form";
        }
        sedeRepository.save(sede);
        redirectAttributes.addFlashAttribute("mensagem", "Sede salva com sucesso!");
        return "redirect:/sedes";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            sedeRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Sede excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir sede. Verifique se não há veículos associados.");
        }
        return "redirect:/sedes";
    }
}
