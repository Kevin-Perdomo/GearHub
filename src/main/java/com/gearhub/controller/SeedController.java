package com.gearhub.controller;

import com.gearhub.config.DatabaseSeederService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/seed")
@RequiredArgsConstructor
@Tag(name = "Seed", description = "Endpoints para popular e limpar o banco de dados. Similar ao comando 'sail seed' do Laravel.")
public class SeedController {

    private final DatabaseSeederService seederService;

    /**
     * Executa o seed do banco de dados.
     * Equivalente ao comando: sail seed (Laravel)
     * 
     * Acesse via navegador: http://localhost:8080/seed/executar
     * Ou via terminal: curl http://localhost:8080/seed/executar
     */
    @GetMapping("/executar")
    @Operation(hidden = true)
    public String executarSeed(RedirectAttributes redirectAttributes) {
        String resultado = seederService.executarSeed();
        redirectAttributes.addFlashAttribute("mensagem", resultado);
        return "redirect:/";
    }

    /**
     * Limpa todos os dados do banco.
     * 
     * Acesse via navegador: http://localhost:8080/seed/limpar
     * Ou via terminal: curl http://localhost:8080/seed/limpar
     */
    @GetMapping("/limpar")
    @Operation(hidden = true)
    public String limparBanco(RedirectAttributes redirectAttributes) {
        String resultado = seederService.limparBanco();
        redirectAttributes.addFlashAttribute("mensagem", resultado);
        return "redirect:/";
    }

    /**
     * Endpoint REST para executar seed via API
     * Uso: curl -X POST http://localhost:8080/seed/api/executar
     */
    @PostMapping("/api/executar")
    @Operation(
        summary = "🌱 Popular Banco de Dados",
        description = "**Popula o banco com dados de teste (equivalente ao `sail seed` do Laravel)**\n\n" +
                "**Dados criados:**\n" +
                "- 2 Empresas (GearHub Transportes e LogiFreight Brasil)\n" +
                "- 6 Sedes (3 por empresa: SP, RJ, MG / PR, RS, SC)\n" +
                "- 24 Veículos (4 por sede - diversos modelos)\n" +
                "- 96 Documentos (IPVA, Licenciamento, Seguro, Revisão)\n\n" +
                "**Proteção:** Verifica se já existem dados antes de executar."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Seed executado com sucesso"),
        @ApiResponse(responseCode = "200", description = "Banco já contém dados - nenhuma ação realizada")
    })
    public ResponseEntity<String> executarSeedApi() {
        String resultado = seederService.executarSeed();
        return ResponseEntity.ok(resultado);
    }

    /**
     * Endpoint REST para limpar banco via API
     * Uso: curl -X POST http://localhost:8080/seed/api/limpar
     */
    @PostMapping("/api/limpar")
    @Operation(
        summary = "🧹 Limpar Banco de Dados",
        description = "**Remove TODOS os dados do banco de dados**\n\n" +
                "**Atenção:** Esta operação não pode ser desfeita!\n\n" +
                "**Remove:**\n" +
                "- Todos os Documentos de Veículos\n" +
                "- Todos os Veículos\n" +
                "- Todas as Sedes\n" +
                "- Todas as Empresas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Banco limpo com sucesso")
    })
    public ResponseEntity<String> limparBancoApi() {
        String resultado = seederService.limparBanco();
        return ResponseEntity.ok(resultado);
    }
}
