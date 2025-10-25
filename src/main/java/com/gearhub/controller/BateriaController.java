package com.gearhub.controller;

import com.gearhub.model.Bateria;
import com.gearhub.repository.BateriaRepository;
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
@RequestMapping("/baterias")
@RequiredArgsConstructor
public class BateriaController {

    private final BateriaRepository bateriaRepository;
    private final VeiculoRepository veiculoRepository;

    @GetMapping
    public String listar(@RequestParam(required = false) Long veiculoId, Model model) {
        if (veiculoId != null) {
            // Filtrar por veículo específico
            model.addAttribute("baterias", bateriaRepository.findByVeiculoId(veiculoId));
            model.addAttribute("veiculo", veiculoRepository.findById(veiculoId).orElse(null));
        } else {
            // Listar todas
            model.addAttribute("baterias", bateriaRepository.findAll());
        }
        return "baterias/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("bateria", new Bateria());
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "baterias/form";
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return bateriaRepository.findById(id)
                .map(bateria -> {
                    model.addAttribute("bateria", bateria);
                    return "baterias/detalhes";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Bateria não encontrada");
                    return "redirect:/baterias";
                });
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return bateriaRepository.findById(id)
                .map(bateria -> {
                    model.addAttribute("bateria", bateria);
                    model.addAttribute("veiculos", veiculoRepository.findAll());
                    return "baterias/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Bateria não encontrada");
                    return "redirect:/baterias";
                });
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Bateria bateria,
                        BindingResult result,
                        @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("veiculos", veiculoRepository.findAll());
            return "baterias/form";
        }
        
        // Processar upload da foto
        if (fotoFile != null && !fotoFile.isEmpty()) {
            try {
                bateria.setFoto(fotoFile.getBytes());
                bateria.setNomeArquivoFoto(fotoFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("erro", "Erro ao processar o arquivo de foto");
                return "redirect:/baterias";
            }
        }
        
        bateriaRepository.save(bateria);
        redirectAttributes.addFlashAttribute("mensagem", "Bateria salva com sucesso!");
        return "redirect:/baterias";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bateriaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Bateria excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir bateria.");
        }
        return "redirect:/baterias";
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) {
        return bateriaRepository.findById(id)
                .map(bateria -> {
                    if (bateria.getFoto() == null) {
                        return ResponseEntity.notFound().<byte[]>build();
                    }
                    
                    // Determinar tipo de conteúdo baseado no nome do arquivo
                    String contentType = MediaType.IMAGE_PNG_VALUE; // padrão
                    if (bateria.getNomeArquivoFoto() != null) {
                        String fileName = bateria.getNomeArquivoFoto().toLowerCase();
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
                    headers.setContentLength(bateria.getFoto().length);
                    
                    return new ResponseEntity<>(bateria.getFoto(), headers, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
