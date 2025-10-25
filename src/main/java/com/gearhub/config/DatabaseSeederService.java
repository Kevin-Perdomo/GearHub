package com.gearhub.config;

import com.gearhub.model.*;
import com.gearhub.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeederService {

    private final EmpresaRepository empresaRepository;
    private final SedeRepository sedeRepository;
    private final VeiculoRepository veiculoRepository;
    private final DocumentoRepository documentoRepository;
    private final AutonomiaRepository autonomiaRepository;
    private final BateriaRepository bateriaRepository;
    private final PneuRepository pneuRepository;
    private final OleoRepository oleoRepository;

    @Transactional
    public String executarSeed() {
        // Verifica se já existe dados
        if (empresaRepository.count() > 0) {
            log.info("Banco de dados já possui dados. Seed não será executado.");
            return "Banco de dados já contém dados. Nenhuma ação foi realizada.";
        }

        log.info("Iniciando seed do banco de dados...");

        // ========== EMPRESA 1 ==========
        Empresa empresa1 = new Empresa();
        empresa1.setNome("GearHub Transportes Ltda");
        empresa1.setCnpj("12.345.678/0001-99");
        empresa1.setLogo(gerarImagemExemplo());
        empresa1.setNomeArquivoLogo("logo-gearhub-transportes.png");
        empresa1 = empresaRepository.save(empresa1);
        log.info("Empresa 1 criada: {}", empresa1.getNome());

        // Sedes da Empresa 1
        Sede sede1_1 = criarSede("Sede Central - SP", "Av. Paulista, 1000 - São Paulo/SP", empresa1);
        Sede sede1_2 = criarSede("Filial Rio de Janeiro", "Av. Rio Branco, 500 - Rio de Janeiro/RJ", empresa1);
        Sede sede1_3 = criarSede("Filial Belo Horizonte", "Av. Afonso Pena, 300 - Belo Horizonte/MG", empresa1);

        // Veículos da Sede 1.1 (SP)
        Veiculo v1_1_1 = criarVeiculo("Fiat", "Fiorino", "ABC-1234", 2020, sede1_1);
        criarAutonomia(v1_1_1, "Gasolina", 50.0, 5.0);
        criarDocumentos(v1_1_1);
        
        Veiculo v1_1_2 = criarVeiculo("Volkswagen", "Saveiro", "ABC-1235", 2021, sede1_1);
        criarAutonomia(v1_1_2, "Flex", 55.0, 6.0);
        criarDocumentos(v1_1_2);
        
        Veiculo v1_1_3 = criarVeiculo("Chevrolet", "Montana", "ABC-1236", 2022, sede1_1);
        criarAutonomia(v1_1_3, "Flex", 54.0, 5.5);
        criarDocumentos(v1_1_3);
        
        Veiculo v1_1_4 = criarVeiculo("Ford", "Courier", "ABC-1237", 2020, sede1_1);
        criarAutonomia(v1_1_4, "Flex", 52.0, 5.0);
        criarDocumentos(v1_1_4);

        // Veículos da Sede 1.2 (RJ)
        Veiculo v1_2_1 = criarVeiculo("Mercedes-Benz", "Sprinter", "DEF-5678", 2022, sede1_2);
        criarAutonomia(v1_2_1, "Diesel", 75.0, 8.0);
        criarDocumentos(v1_2_1);
        
        Veiculo v1_2_2 = criarVeiculo("Renault", "Master", "DEF-5679", 2021, sede1_2);
        criarAutonomia(v1_2_2, "Diesel", 90.0, 10.0);
        criarDocumentos(v1_2_2);
        
        Veiculo v1_2_3 = criarVeiculo("Iveco", "Daily", "DEF-5680", 2023, sede1_2);
        criarAutonomia(v1_2_3, "Diesel", 80.0, 9.0);
        criarDocumentos(v1_2_3);
        
        Veiculo v1_2_4 = criarVeiculo("Peugeot", "Boxer", "DEF-5681", 2022, sede1_2);
        criarAutonomia(v1_2_4, "Diesel", 85.0, 8.5);
        criarDocumentos(v1_2_4);

        // Veículos da Sede 1.3 (BH)
        Veiculo v1_3_1 = criarVeiculo("Fiat", "Ducato", "GHI-9012", 2021, sede1_3);
        criarAutonomia(v1_3_1, "Diesel", 90.0, 10.0);
        criarDocumentos(v1_3_1);
        
        Veiculo v1_3_2 = criarVeiculo("Citroën", "Jumper", "GHI-9013", 2022, sede1_3);
        criarAutonomia(v1_3_2, "Diesel", 88.0, 9.5);
        criarDocumentos(v1_3_2);
        
        Veiculo v1_3_3 = criarVeiculo("Volkswagen", "Delivery", "GHI-9014", 2020, sede1_3);
        criarAutonomia(v1_3_3, "Diesel", 100.0, 12.0);
        criarDocumentos(v1_3_3);
        
        Veiculo v1_3_4 = criarVeiculo("Ford", "Transit", "GHI-9015", 2023, sede1_3);
        criarAutonomia(v1_3_4, "Diesel", 80.0, 8.0);
        criarDocumentos(v1_3_4);

        // ========== EMPRESA 2 ==========
        Empresa empresa2 = new Empresa();
        empresa2.setNome("LogiFreight Brasil S.A.");
        empresa2.setCnpj("98.765.432/0001-10");
        empresa2.setLogo(gerarImagemExemplo());
        empresa2.setNomeArquivoLogo("logo-logifreight.png");
        empresa2 = empresaRepository.save(empresa2);
        log.info("Empresa 2 criada: {}", empresa2.getNome());

        // Sedes da Empresa 2
        Sede sede2_1 = criarSede("Matriz Curitiba", "Rua XV de Novembro, 800 - Curitiba/PR", empresa2);
        Sede sede2_2 = criarSede("Filial Porto Alegre", "Av. Borges de Medeiros, 200 - Porto Alegre/RS", empresa2);
        Sede sede2_3 = criarSede("Filial Florianópolis", "Av. Beira Mar Norte, 100 - Florianópolis/SC", empresa2);

        // Veículos da Sede 2.1 (Curitiba)
        Veiculo v2_1_1 = criarVeiculo("Hyundai", "HR", "JKL-3456", 2022, sede2_1);
        criarAutonomia(v2_1_1, "Diesel", 60.0, 7.0);
        criarDocumentos(v2_1_1);
        
        Veiculo v2_1_2 = criarVeiculo("JAC", "X200", "JKL-3457", 2021, sede2_1);
        criarAutonomia(v2_1_2, "Diesel", 65.0, 7.5);
        criarDocumentos(v2_1_2);
        
        Veiculo v2_1_3 = criarVeiculo("Kia", "Bongo", "JKL-3458", 2023, sede2_1);
        criarAutonomia(v2_1_3, "Diesel", 62.0, 7.0);
        criarDocumentos(v2_1_3);
        
        Veiculo v2_1_4 = criarVeiculo("Toyota", "Hilux", "JKL-3459", 2022, sede2_1);
        criarAutonomia(v2_1_4, "Diesel", 80.0, 9.0);
        criarDocumentos(v2_1_4);

        // Veículos da Sede 2.2 (Porto Alegre)
        Veiculo v2_2_1 = criarVeiculo("Volkswagen", "Amarok", "MNO-7890", 2023, sede2_2);
        criarAutonomia(v2_2_1, "Diesel", 80.0, 8.0);
        criarDocumentos(v2_2_1);
        
        Veiculo v2_2_2 = criarVeiculo("Nissan", "Frontier", "MNO-7891", 2022, sede2_2);
        criarAutonomia(v2_2_2, "Diesel", 80.0, 8.5);
        criarDocumentos(v2_2_2);
        
        Veiculo v2_2_3 = criarVeiculo("Mitsubishi", "L200", "MNO-7892", 2021, sede2_2);
        criarAutonomia(v2_2_3, "Diesel", 75.0, 8.0);
        criarDocumentos(v2_2_3);
        
        Veiculo v2_2_4 = criarVeiculo("Chevrolet", "S10", "MNO-7893", 2023, sede2_2);
        criarAutonomia(v2_2_4, "Diesel", 76.0, 8.2);
        criarDocumentos(v2_2_4);

        // Veículos da Sede 2.3 (Florianópolis)
        Veiculo v2_3_1 = criarVeiculo("Ram", "2500", "PQR-2468", 2023, sede2_3);
        criarAutonomia(v2_3_1, "Diesel", 100.0, 12.0);
        criarDocumentos(v2_3_1);
        
        Veiculo v2_3_2 = criarVeiculo("Ford", "Ranger", "PQR-2469", 2022, sede2_3);
        criarAutonomia(v2_3_2, "Diesel", 80.0, 9.0);
        criarDocumentos(v2_3_2);
        
        Veiculo v2_3_3 = criarVeiculo("Fiat", "Toro", "PQR-2470", 2023, sede2_3);
        criarAutonomia(v2_3_3, "Diesel", 60.0, 7.0);
        criarDocumentos(v2_3_3);
        
        Veiculo v2_3_4 = criarVeiculo("Jeep", "Gladiator", "PQR-2471", 2023, sede2_3);
        criarAutonomia(v2_3_4, "Diesel", 85.0, 9.5);
        criarDocumentos(v2_3_4);

        log.info("Seed do banco de dados concluído com sucesso!");
        
        return "Seed executado com sucesso! 2 Empresas, 6 Sedes, 24 Veículos, 24 Autonomias e 96 Documentos criados.";
    }

    private Sede criarSede(String nome, String endereco, Empresa empresa) {
        Sede sede = new Sede();
        sede.setNome(nome);
        sede.setEndereco(endereco);
        sede.setEmpresa(empresa);
        sede.setFoto(gerarImagemExemplo());
        sede.setNomeArquivoFoto("foto-" + nome.toLowerCase().replaceAll("[^a-z0-9]", "-") + ".png");
        sede = sedeRepository.save(sede);
        log.info("Sede criada: {} - {}", sede.getNome(), empresa.getNome());
        return sede;
    }

    private Veiculo criarVeiculo(String marca, String modelo, String placa, int anoModelo, Sede sede) {
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setPlaca(placa);
        veiculo.setAnoModelo(anoModelo);
        veiculo.setSede(sede);
        veiculo.setFoto(gerarImagemExemplo());
        veiculo.setNomeArquivoFoto("veiculo-" + placa.toLowerCase().replace("-", "") + ".png");
        veiculo = veiculoRepository.save(veiculo);
        log.info("Veículo criado: {} {} ({}) - {}", marca, modelo, placa, sede.getNome());
        return veiculo;
    }

    private void criarAutonomia(Veiculo veiculo, String combustivel, double capacidadeTanque, double capacidadeReserva) {
        Autonomia autonomia = new Autonomia();
        autonomia.setVeiculo(veiculo);
        autonomia.setCombustivelRecomendado(combustivel);
        autonomia.setCapacidadeTanqueLitros(capacidadeTanque);
        autonomia.setCapacidadeReservaLitros(capacidadeReserva);
        autonomiaRepository.save(autonomia);
        log.info("Autonomia criada para veículo: {} ({}, {}L, Reserva: {}L)", 
                 veiculo.getPlaca(), combustivel, capacidadeTanque, capacidadeReserva);
    }

    private void criarDocumentos(Veiculo veiculo) {
        // IPVA 2024
        Documento ipva = new Documento();
        ipva.setVeiculo(veiculo);
        ipva.setTipoDocumento("IPVA");
        ipva.setAnoReferencia(2024);
        ipva.setStatus("Pago");
        ipva.setDataPagamento(LocalDate.of(2024, 1, 10));
        ipva.setDataUpload(LocalDateTime.now());
        ipva.setArquivoPdf(gerarPdfExemplo("IPVA " + veiculo.getPlaca()));
        ipva.setNomeArquivo("ipva-2024-" + veiculo.getPlaca().toLowerCase().replace("-", "") + ".pdf");
        documentoRepository.save(ipva);

        // Licenciamento 2024 (CRLV)
        Documento crlv = new Documento();
        crlv.setVeiculo(veiculo);
        crlv.setTipoDocumento("CRLV");
        crlv.setAnoReferencia(2024);
        crlv.setStatus("Pago");
        crlv.setDataPagamento(LocalDate.of(2024, 1, 15));
        crlv.setDataUpload(LocalDateTime.now());
        crlv.setArquivoPdf(gerarPdfExemplo("CRLV " + veiculo.getPlaca()));
        crlv.setNomeArquivo("crlv-2024-" + veiculo.getPlaca().toLowerCase().replace("-", "") + ".pdf");
        documentoRepository.save(crlv);

        // Seguro
        Documento seguro = new Documento();
        seguro.setVeiculo(veiculo);
        seguro.setTipoDocumento("SEGURO");
        seguro.setAnoReferencia(2024);
        seguro.setStatus("Pago");
        seguro.setDataPagamento(LocalDate.of(2024, 2, 1));
        seguro.setDataUpload(LocalDateTime.now());
        seguro.setArquivoPdf(gerarPdfExemplo("Seguro " + veiculo.getPlaca()));
        seguro.setNomeArquivo("seguro-2024-" + veiculo.getPlaca().toLowerCase().replace("-", "") + ".pdf");
        documentoRepository.save(seguro);

        // Manual do Proprietário
        Documento manual = new Documento();
        manual.setVeiculo(veiculo);
        manual.setTipoDocumento("MANUAL");
        manual.setStatus("Ativo");
        manual.setDataUpload(LocalDateTime.now());
        manual.setArquivoPdf(gerarPdfExemplo("Manual " + veiculo.getMarca() + " " + veiculo.getModelo()));
        manual.setNomeArquivo("manual-" + veiculo.getMarca().toLowerCase() + "-" + veiculo.getModelo().toLowerCase() + ".pdf");
        documentoRepository.save(manual);

        log.info("4 Documentos criados para veículo: {} (IPVA, CRLV, SEGURO, MANUAL)", veiculo.getPlaca());
    }

    @Transactional
    public String limparBanco() {
        log.info("Limpando banco de dados...");
        
        oleoRepository.deleteAll();
        pneuRepository.deleteAll();
        bateriaRepository.deleteAll();
        documentoRepository.deleteAll();
        autonomiaRepository.deleteAll();
        veiculoRepository.deleteAll();
        sedeRepository.deleteAll();
        empresaRepository.deleteAll();
        
        log.info("Banco de dados limpo com sucesso!");
        
        return "Banco de dados limpo com sucesso!";
    }

    /**
     * Gera uma imagem PNG simples (1x1 pixel azul) para usar como exemplo
     */
    private byte[] gerarImagemExemplo() {
        // PNG 1x1 pixel azul (#1565c0) em base64
        String pngBase64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg==";
        return Base64.getDecoder().decode(pngBase64);
    }

    /**
     * Gera um PDF mínimo válido para usar como exemplo
     */
    private byte[] gerarPdfExemplo(String titulo) {
        // PDF mínimo válido
        String pdf = "%PDF-1.4\n" +
                "1 0 obj<</Type/Catalog/Pages 2 0 R>>endobj\n" +
                "2 0 obj<</Type/Pages/Count 1/Kids[3 0 R]>>endobj\n" +
                "3 0 obj<</Type/Page/MediaBox[0 0 612 792]/Parent 2 0 R/Resources<<>>>>endobj\n" +
                "xref\n0 4\n0000000000 65535 f\n0000000009 00000 n\n0000000056 00000 n\n0000000115 00000 n\n" +
                "trailer<</Size 4/Root 1 0 R>>\nstartxref\n199\n%%EOF";
        return pdf.getBytes();
    }
}
