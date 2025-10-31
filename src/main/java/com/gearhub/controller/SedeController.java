package com.gearhub.controller;

import com.gearhub.model.Sede;
import com.gearhub.repository.EmpresaRepository;
import com.gearhub.repository.SedeRepository;
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
    public String salvar(@ModelAttribute Sede sede,
                        BindingResult result,
                        @RequestParam(value = "empresaId", required = false) Long empresaId,
                        @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        System.out.println("=== DEBUG SALVAR SEDE ===");
        System.out.println("Empresa ID recebido: " + empresaId);
        System.out.println("Nome: " + sede.getNome());
        System.out.println("Endereço: " + sede.getEndereco());
        
        // Buscar e associar a empresa antes da validação
        if (empresaId != null) {
            empresaRepository.findById(empresaId).ifPresentOrElse(
                empresa -> {
                    sede.setEmpresa(empresa);
                    System.out.println("Empresa associada: " + empresa.getNome());
                },
                () -> System.out.println("Empresa não encontrada com ID: " + empresaId)
            );
        } else {
            System.out.println("empresaId é NULL!");
        }
        
        // Validar campos manualmente
        if (sede.getNome() == null || sede.getNome().trim().isEmpty()) {
            result.rejectValue("nome", "error.sede", "Nome é obrigatório");
        }
        
        if (sede.getEmpresa() == null) {
            result.rejectValue("empresa", "error.sede", "Empresa é obrigatória");
            System.out.println("ERRO: Empresa não foi associada!");
        }
        
        if (result.hasErrors()) {
            System.out.println("Erros encontrados: " + result.getAllErrors());
            model.addAttribute("empresas", empresaRepository.findAll());
            return "sedes/form";
        }
        
        // Processar upload da foto
        if (fotoFile != null && !fotoFile.isEmpty()) {
            try {
                sede.setFoto(fotoFile.getBytes());
                sede.setNomeArquivoFoto(fotoFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("erro", "Erro ao processar o arquivo de foto");
                model.addAttribute("empresas", empresaRepository.findAll());
                return "sedes/form";
            }
        }
        
        sedeRepository.save(sede);
        System.out.println("Sede salva com sucesso! ID: " + sede.getId());
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

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sede não encontrada"));
        
        if (sede.getFoto() == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Determinar tipo de conteúdo baseado no nome do arquivo
        String contentType = MediaType.IMAGE_PNG_VALUE; // padrão
        if (sede.getNomeArquivoFoto() != null) {
            String fileName = sede.getNomeArquivoFoto().toLowerCase();
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
        headers.setContentLength(sede.getFoto().length);
        
        return new ResponseEntity<>(sede.getFoto(), headers, HttpStatus.OK);
    }
}
