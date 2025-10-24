package com.gearhub.controller;

import com.gearhub.model.Empresa;
import com.gearhub.repository.EmpresaRepository;
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

import jakarta.validation.Valid;
import java.io.IOException;

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
                        @RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "empresas/form";
        }
        
        // Processar upload do logo
        if (logoFile != null && !logoFile.isEmpty()) {
            try {
                empresa.setLogo(logoFile.getBytes());
                empresa.setNomeArquivoLogo(logoFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("erro", "Erro ao processar o arquivo de logo");
                return "redirect:/empresas";
            }
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

    @GetMapping("/{id}/logo")
    public ResponseEntity<byte[]> getLogo(@PathVariable Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
        
        if (empresa.getLogo() == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Determinar tipo de conteúdo baseado no nome do arquivo
        String contentType = MediaType.IMAGE_PNG_VALUE; // padrão
        if (empresa.getNomeArquivoLogo() != null) {
            String fileName = empresa.getNomeArquivoLogo().toLowerCase();
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
        headers.setContentLength(empresa.getLogo().length);
        
        return new ResponseEntity<>(empresa.getLogo(), headers, HttpStatus.OK);
    }
}
