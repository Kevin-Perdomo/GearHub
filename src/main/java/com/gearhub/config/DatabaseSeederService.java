package com.gearhub.config;

import com.gearhub.model.*;
import com.gearhub.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeederService {

    private final EmpresaRepository empresaRepository;
    private final SedeRepository sedeRepository;
    private final VeiculoRepository veiculoRepository;
    private final DocumentoRepository documentoRepository;

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
        empresa1 = empresaRepository.save(empresa1);
        log.info("Empresa 1 criada: {}", empresa1.getNome());

        // Sedes da Empresa 1
        Sede sede1_1 = criarSede("Sede Central - SP", "Av. Paulista, 1000 - São Paulo/SP", empresa1);
        Sede sede1_2 = criarSede("Filial Rio de Janeiro", "Av. Rio Branco, 500 - Rio de Janeiro/RJ", empresa1);
        Sede sede1_3 = criarSede("Filial Belo Horizonte", "Av. Afonso Pena, 300 - Belo Horizonte/MG", empresa1);

        // Veículos da Sede 1.1 (SP)
        Veiculo v1_1_1 = criarVeiculo("Fiat", "Fiorino", "ABC-1234", 2020, "Gasolina", 50.0, 5.0, sede1_1);
        criarDocumentos(v1_1_1);
        
        Veiculo v1_1_2 = criarVeiculo("Volkswagen", "Saveiro", "ABC-1235", 2021, "Flex", 55.0, 6.0, sede1_1);
        criarDocumentos(v1_1_2);
        
        Veiculo v1_1_3 = criarVeiculo("Chevrolet", "Montana", "ABC-1236", 2022, "Flex", 54.0, 5.5, sede1_1);
        criarDocumentos(v1_1_3);
        
        Veiculo v1_1_4 = criarVeiculo("Ford", "Courier", "ABC-1237", 2020, "Flex", 52.0, 5.0, sede1_1);
        criarDocumentos(v1_1_4);

        // Veículos da Sede 1.2 (RJ)
        Veiculo v1_2_1 = criarVeiculo("Mercedes-Benz", "Sprinter", "DEF-5678", 2022, "Diesel", 75.0, 8.0, sede1_2);
        criarDocumentos(v1_2_1);
        
        Veiculo v1_2_2 = criarVeiculo("Renault", "Master", "DEF-5679", 2021, "Diesel", 90.0, 10.0, sede1_2);
        criarDocumentos(v1_2_2);
        
        Veiculo v1_2_3 = criarVeiculo("Iveco", "Daily", "DEF-5680", 2023, "Diesel", 80.0, 9.0, sede1_2);
        criarDocumentos(v1_2_3);
        
        Veiculo v1_2_4 = criarVeiculo("Peugeot", "Boxer", "DEF-5681", 2022, "Diesel", 85.0, 8.5, sede1_2);
        criarDocumentos(v1_2_4);

        // Veículos da Sede 1.3 (BH)
        Veiculo v1_3_1 = criarVeiculo("Fiat", "Ducato", "GHI-9012", 2021, "Diesel", 90.0, 10.0, sede1_3);
        criarDocumentos(v1_3_1);
        
        Veiculo v1_3_2 = criarVeiculo("Citroën", "Jumper", "GHI-9013", 2022, "Diesel", 88.0, 9.5, sede1_3);
        criarDocumentos(v1_3_2);
        
        Veiculo v1_3_3 = criarVeiculo("Volkswagen", "Delivery", "GHI-9014", 2020, "Diesel", 100.0, 12.0, sede1_3);
        criarDocumentos(v1_3_3);
        
        Veiculo v1_3_4 = criarVeiculo("Ford", "Transit", "GHI-9015", 2023, "Diesel", 80.0, 8.0, sede1_3);
        criarDocumentos(v1_3_4);

        // ========== EMPRESA 2 ==========
        Empresa empresa2 = new Empresa();
        empresa2.setNome("LogiFreight Brasil S.A.");
        empresa2.setCnpj("98.765.432/0001-10");
        empresa2 = empresaRepository.save(empresa2);
        log.info("Empresa 2 criada: {}", empresa2.getNome());

        // Sedes da Empresa 2
        Sede sede2_1 = criarSede("Matriz Curitiba", "Rua XV de Novembro, 800 - Curitiba/PR", empresa2);
        Sede sede2_2 = criarSede("Filial Porto Alegre", "Av. Borges de Medeiros, 200 - Porto Alegre/RS", empresa2);
        Sede sede2_3 = criarSede("Filial Florianópolis", "Av. Beira Mar Norte, 100 - Florianópolis/SC", empresa2);

        // Veículos da Sede 2.1 (Curitiba)
        Veiculo v2_1_1 = criarVeiculo("Hyundai", "HR", "JKL-3456", 2022, "Diesel", 60.0, 7.0, sede2_1);
        criarDocumentos(v2_1_1);
        
        Veiculo v2_1_2 = criarVeiculo("JAC", "X200", "JKL-3457", 2021, "Diesel", 65.0, 7.5, sede2_1);
        criarDocumentos(v2_1_2);
        
        Veiculo v2_1_3 = criarVeiculo("Kia", "Bongo", "JKL-3458", 2023, "Diesel", 62.0, 7.0, sede2_1);
        criarDocumentos(v2_1_3);
        
        Veiculo v2_1_4 = criarVeiculo("Toyota", "Hilux", "JKL-3459", 2022, "Diesel", 80.0, 9.0, sede2_1);
        criarDocumentos(v2_1_4);

        // Veículos da Sede 2.2 (Porto Alegre)
        Veiculo v2_2_1 = criarVeiculo("Volkswagen", "Amarok", "MNO-7890", 2023, "Diesel", 80.0, 8.0, sede2_2);
        criarDocumentos(v2_2_1);
        
        Veiculo v2_2_2 = criarVeiculo("Nissan", "Frontier", "MNO-7891", 2022, "Diesel", 80.0, 8.5, sede2_2);
        criarDocumentos(v2_2_2);
        
        Veiculo v2_2_3 = criarVeiculo("Mitsubishi", "L200", "MNO-7892", 2021, "Diesel", 75.0, 8.0, sede2_2);
        criarDocumentos(v2_2_3);
        
        Veiculo v2_2_4 = criarVeiculo("Chevrolet", "S10", "MNO-7893", 2023, "Diesel", 76.0, 8.2, sede2_2);
        criarDocumentos(v2_2_4);

        // Veículos da Sede 2.3 (Florianópolis)
        Veiculo v2_3_1 = criarVeiculo("Ram", "2500", "PQR-2468", 2023, "Diesel", 100.0, 12.0, sede2_3);
        criarDocumentos(v2_3_1);
        
        Veiculo v2_3_2 = criarVeiculo("Ford", "Ranger", "PQR-2469", 2022, "Diesel", 80.0, 9.0, sede2_3);
        criarDocumentos(v2_3_2);
        
        Veiculo v2_3_3 = criarVeiculo("Fiat", "Toro", "PQR-2470", 2023, "Diesel", 60.0, 7.0, sede2_3);
        criarDocumentos(v2_3_3);
        
        Veiculo v2_3_4 = criarVeiculo("Jeep", "Gladiator", "PQR-2471", 2023, "Diesel", 85.0, 9.5, sede2_3);
        criarDocumentos(v2_3_4);

        log.info("Seed do banco de dados concluído com sucesso!");
        
        return "Seed executado com sucesso! 2 Empresas, 6 Sedes, 24 Veículos e 96 Documentos criados.";
    }

    private Sede criarSede(String nome, String endereco, Empresa empresa) {
        Sede sede = new Sede();
        sede.setNome(nome);
        sede.setEndereco(endereco);
        sede.setEmpresa(empresa);
        sede = sedeRepository.save(sede);
        log.info("Sede criada: {} - {}", sede.getNome(), empresa.getNome());
        return sede;
    }

    private Veiculo criarVeiculo(String marca, String modelo, String placa, int anoModelo, 
                                  String combustivel, double capacidadeTanque, double capacidadeReserva, Sede sede) {
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setPlaca(placa);
        veiculo.setAnoModelo(anoModelo);
        veiculo.setCombustivelRecomendado(combustivel);
        veiculo.setCapacidadeTanqueLitros(capacidadeTanque);
        veiculo.setCapacidadeReservaLitros(capacidadeReserva);
        veiculo.setSede(sede);
        veiculo = veiculoRepository.save(veiculo);
        log.info("Veículo criado: {} {} ({}) - {}", marca, modelo, placa, sede.getNome());
        return veiculo;
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
        documentoRepository.save(ipva);

        // Licenciamento 2024
        Documento licenciamento = new Documento();
        licenciamento.setVeiculo(veiculo);
        licenciamento.setTipoDocumento("CRLV - Licenciamento");
        licenciamento.setAnoReferencia(2024);
        licenciamento.setStatus("Pago");
        licenciamento.setDataPagamento(LocalDate.of(2024, 1, 15));
        licenciamento.setDataUpload(LocalDateTime.now());
        documentoRepository.save(licenciamento);

        // Seguro
        Documento seguro = new Documento();
        seguro.setVeiculo(veiculo);
        seguro.setTipoDocumento("Seguro Veicular");
        seguro.setAnoReferencia(2024);
        seguro.setStatus("Pago");
        seguro.setDataPagamento(LocalDate.of(2024, 2, 1));
        seguro.setDataUpload(LocalDateTime.now());
        documentoRepository.save(seguro);

        // Revisão
        Documento revisao = new Documento();
        revisao.setVeiculo(veiculo);
        revisao.setTipoDocumento("Revisão Anual");
        revisao.setAnoReferencia(2024);
        revisao.setStatus("Pendente");
        revisao.setDataUpload(LocalDateTime.now());
        documentoRepository.save(revisao);

        log.info("4 Documentos criados para veículo: {}", veiculo.getPlaca());
    }

    @Transactional
    public String limparBanco() {
        log.info("Limpando banco de dados...");
        
        documentoRepository.deleteAll();
        veiculoRepository.deleteAll();
        sedeRepository.deleteAll();
        empresaRepository.deleteAll();
        
        log.info("Banco de dados limpo com sucesso!");
        
        return "Banco de dados limpo com sucesso!";
    }
}
