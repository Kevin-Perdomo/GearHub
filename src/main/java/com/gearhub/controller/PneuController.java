package com.gearhub.controller;

import com.gearhub.model.Pneu;
import com.gearhub.repository.PneuRepository;
import com.gearhub.repository.VeiculoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/pneus")
@RequiredArgsConstructor
public class PneuController {

    private final PneuRepository pneuRepository;
    private final VeiculoRepository veiculoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pneus", pneuRepository.findAll());
        return "pneus/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pneu", new Pneu());
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "pneus/form";
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        Pneu pneu = pneuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pneu não encontrado"));
        model.addAttribute("pneu", pneu);
        return "pneus/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Pneu pneu = pneuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pneu não encontrado"));
        model.addAttribute("pneu", pneu);
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "pneus/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Pneu pneu,
                        BindingResult result,
                        @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("veiculos", veiculoRepository.findAll());
            return "pneus/form";
        }
        
        // Processar upload da foto
        if (fotoFile != null && !fotoFile.isEmpty()) {
            try {
                pneu.setFoto(fotoFile.getBytes());
                pneu.setNomeArquivoFoto(fotoFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("erro", "Erro ao processar o arquivo de foto");
                return "redirect:/pneus";
            }
        }
        
        pneuRepository.save(pneu);
        redirectAttributes.addFlashAttribute("mensagem", "Pneu salvo com sucesso!");
        return "redirect:/pneus";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            pneuRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Pneu excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir pneu.");
        }
        return "redirect:/pneus";
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) {
        Pneu pneu = pneuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pneu não encontrado"));
        
        if (pneu.getFoto() == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Determinar tipo de conteúdo baseado no nome do arquivo
        String contentType = MediaType.IMAGE_PNG_VALUE; // padrão
        if (pneu.getNomeArquivoFoto() != null) {
            String fileName = pneu.getNomeArquivoFoto().toLowerCase();
            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                contentType = MediaType.IMAGE_JPEG_VALUE;
            } else if (fileName.endsWith(".png")) {
                contentType = MediaType.IMAGE_PNG_VALUE;
            } else if (fileName.endsWith(".gif")) {
                contentType = MediaType.IMAGE_GIF_VALUE;
            }
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(pneu.getFoto().length);
        
        return new ResponseEntity<>(pneu.getFoto(), headers, HttpStatus.OK);
    }
}
