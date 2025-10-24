package com.gearhub.controller;

import com.gearhub.model.Documento;
import com.gearhub.repository.DocumentoRepository;
import com.gearhub.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/documentos-veiculo")
@RequiredArgsConstructor
public class DocumentoVeiculoController {

    private final DocumentoRepository documentoRepository;
    private final VeiculoRepository veiculoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("documentos", documentoRepository.findAll());
        return "documentos-veiculo/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("documento", new Documento());
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "documentos-veiculo/form";
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Documento não encontrado"));
        model.addAttribute("documento", documento);
        return "documentos-veiculo/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Documento não encontrado"));
        model.addAttribute("documento", documento);
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "documentos-veiculo/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Documento documento, 
                        BindingResult result, 
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("veiculos", veiculoRepository.findAll());
            return "documentos-veiculo/form";
        }
        documentoRepository.save(documento);
        redirectAttributes.addFlashAttribute("mensagem", "Documento salvo com sucesso!");
        return "redirect:/documentos-veiculo";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        documentoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Documento excluído com sucesso!");
        return "redirect:/documentos-veiculo";
    }
}
