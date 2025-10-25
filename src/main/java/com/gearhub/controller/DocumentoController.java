package com.gearhub.controller;

import com.gearhub.model.Documento;
import com.gearhub.repository.DocumentoRepository;
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
import java.time.LocalDateTime;

@Controller
@RequestMapping("/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoRepository documentoRepository;
    private final VeiculoRepository veiculoRepository;

    @GetMapping
    public String listar(@RequestParam(required = false) Long veiculoId, Model model) {
        if (veiculoId != null) {
            // Filtrar por veículo específico
            model.addAttribute("documentos", documentoRepository.findByVeiculoId(veiculoId));
            model.addAttribute("veiculo", veiculoRepository.findById(veiculoId).orElse(null));
        } else {
            // Listar todos
            model.addAttribute("documentos", documentoRepository.findAll());
        }
        return "documentos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("documento", new Documento());
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "documentos/form";
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return documentoRepository.findById(id)
                .map(documento -> {
                    model.addAttribute("documento", documento);
                    return "documentos/detalhes";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Documento não encontrado");
                    return "redirect:/documentos";
                });
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return documentoRepository.findById(id)
                .map(documento -> {
                    model.addAttribute("documento", documento);
                    model.addAttribute("veiculos", veiculoRepository.findAll());
                    return "documentos/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Documento não encontrado");
                    return "redirect:/documentos";
                });
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Documento documento,
                        BindingResult result,
                        @RequestParam(value = "arquivoPdfFile", required = false) MultipartFile arquivoPdfFile,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("veiculos", veiculoRepository.findAll());
            return "documentos/form";
        }
        
        // Processar upload do PDF
        if (arquivoPdfFile != null && !arquivoPdfFile.isEmpty()) {
            try {
                documento.setArquivoPdf(arquivoPdfFile.getBytes());
                documento.setNomeArquivo(arquivoPdfFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("erro", "Erro ao processar o arquivo PDF");
                return "redirect:/documentos";
            }
        }
        
        // Definir data de upload se for um novo documento
        if (documento.getId() == null && documento.getDataUpload() == null) {
            documento.setDataUpload(LocalDateTime.now());
        }
        
        documentoRepository.save(documento);
        redirectAttributes.addFlashAttribute("mensagem", "Documento salvo com sucesso!");
        return "redirect:/documentos";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            documentoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Documento excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir documento.");
        }
        return "redirect:/documentos";
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> getPdf(@PathVariable Long id) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Documento não encontrado"));
        
        if (documento.getArquivoPdf() == null) {
            return ResponseEntity.notFound().build();
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(documento.getArquivoPdf().length);
        
        // Sugerir nome do arquivo para download
        if (documento.getNomeArquivo() != null) {
            headers.setContentDispositionFormData("inline", documento.getNomeArquivo());
        } else {
            headers.setContentDispositionFormData("inline", "documento-" + documento.getId() + ".pdf");
        }
        
        return new ResponseEntity<>(documento.getArquivoPdf(), headers, HttpStatus.OK);
    }
}
