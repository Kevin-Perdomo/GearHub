package com.gearhub.controller;

import com.gearhub.model.Oleo;
import com.gearhub.repository.OleoRepository;
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
@RequestMapping("/oleos")
@RequiredArgsConstructor
public class OleoController {

    private final OleoRepository oleoRepository;
    private final VeiculoRepository veiculoRepository;

    @GetMapping
    public String listar(@RequestParam(required = false) Long veiculoId, Model model) {
        if (veiculoId != null) {
            // Filtrar por veículo específico
            model.addAttribute("oleos", oleoRepository.findByVeiculoId(veiculoId));
            model.addAttribute("veiculo", veiculoRepository.findById(veiculoId).orElse(null));
        } else {
            // Listar todos
            model.addAttribute("oleos", oleoRepository.findAll());
        }
        return "oleos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("oleo", new Oleo());
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "oleos/form";
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        Oleo oleo = oleoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Óleo não encontrado"));
        model.addAttribute("oleo", oleo);
        return "oleos/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Oleo oleo = oleoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Óleo não encontrado"));
        model.addAttribute("oleo", oleo);
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "oleos/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Oleo oleo,
                        BindingResult result,
                        @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("veiculos", veiculoRepository.findAll());
            return "oleos/form";
        }
        
        // Processar upload da foto
        if (fotoFile != null && !fotoFile.isEmpty()) {
            try {
                oleo.setFoto(fotoFile.getBytes());
                oleo.setNomeArquivoFoto(fotoFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("erro", "Erro ao processar o arquivo de foto");
                return "redirect:/oleos";
            }
        }
        
        oleoRepository.save(oleo);
        redirectAttributes.addFlashAttribute("mensagem", "Troca de óleo salva com sucesso!");
        return "redirect:/oleos";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            oleoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Troca de óleo excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir troca de óleo.");
        }
        return "redirect:/oleos";
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) {
        Oleo oleo = oleoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Óleo não encontrado"));
        
        if (oleo.getFoto() == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Determinar tipo de conteúdo baseado no nome do arquivo
        String contentType = MediaType.IMAGE_PNG_VALUE; // padrão
        if (oleo.getNomeArquivoFoto() != null) {
            String fileName = oleo.getNomeArquivoFoto().toLowerCase();
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
        headers.setContentLength(oleo.getFoto().length);
        
        return new ResponseEntity<>(oleo.getFoto(), headers, HttpStatus.OK);
    }
}
