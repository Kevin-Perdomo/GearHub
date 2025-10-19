package com.gearhub.config;

import com.gearhub.model.*;
import com.gearhub.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(
            EmpresaRepository empresaRepository,
            SedeRepository sedeRepository,
            VeiculoRepository veiculoRepository,
            DocumentoVeiculoRepository documentoVeiculoRepository
    ) {
        return args -> {
            // Verifica se já existe dados para não duplicar
            if (empresaRepository.count() > 0) {
                log.info("Banco de dados já possui dados. Seed não será executado.");
                return;
            }

            log.info("Iniciando seed do banco de dados...");

            // 1. Criar Empresa
            Empresa empresa = new Empresa();
            empresa.setNome("GearHub Transportes");
            empresa.setCnpj("12.345.678/0001-99");
            empresa = empresaRepository.save(empresa);
            log.info("Empresa criada: {}", empresa.getNome());

            // 2. Criar Sede
            Sede sede = new Sede();
            sede.setNome("Sede Central");
            sede.setEndereco("Av. Paulista, 1000 - São Paulo/SP");
            sede.setEmpresa(empresa);
            sede = sedeRepository.save(sede);
            log.info("Sede criada: {}", sede.getNome());

            // 3. Criar Veículos
            Veiculo veiculo1 = new Veiculo();
            veiculo1.setModelo("Fiorino");
            veiculo1.setMarca("Fiat");
            veiculo1.setPlaca("ABC-1234");
            veiculo1.setAnoModelo(2020);
            veiculo1.setCombustivelRecomendado("Gasolina");
            veiculo1.setCapacidadeTanqueLitros(50.0);
            veiculo1.setCapacidadeReservaLitros(5.0);
            veiculo1.setSede(sede);
            veiculo1 = veiculoRepository.save(veiculo1);
            log.info("Veículo criado: {} - {}", veiculo1.getMarca(), veiculo1.getModelo());

            Veiculo veiculo2 = new Veiculo();
            veiculo2.setModelo("Sprinter");
            veiculo2.setMarca("Mercedes-Benz");
            veiculo2.setPlaca("XYZ-5678");
            veiculo2.setAnoModelo(2022);
            veiculo2.setCombustivelRecomendado("Diesel");
            veiculo2.setCapacidadeTanqueLitros(75.0);
            veiculo2.setCapacidadeReservaLitros(8.0);
            veiculo2.setSede(sede);
            veiculo2 = veiculoRepository.save(veiculo2);
            log.info("Veículo criado: {} - {}", veiculo2.getMarca(), veiculo2.getModelo());

            Veiculo veiculo3 = new Veiculo();
            veiculo3.setModelo("Master");
            veiculo3.setMarca("Renault");
            veiculo3.setPlaca("DEF-9012");
            veiculo3.setAnoModelo(2021);
            veiculo3.setCombustivelRecomendado("Diesel");
            veiculo3.setCapacidadeTanqueLitros(90.0);
            veiculo3.setCapacidadeReservaLitros(10.0);
            veiculo3.setSede(sede);
            veiculo3 = veiculoRepository.save(veiculo3);
            log.info("Veículo criado: {} - {}", veiculo3.getMarca(), veiculo3.getModelo());

            // 4. Criar Documentos dos Veículos (IPVA, Licenciamento, etc)
            DocumentoVeiculo doc1 = new DocumentoVeiculo();
            doc1.setVeiculo(veiculo1);
            doc1.setTipoDocumento("CRLV - Licenciamento");
            doc1.setAnoReferencia(2024);
            doc1.setStatus("Pago");
            doc1.setDataPagamento(LocalDate.of(2024, 1, 15));
            documentoVeiculoRepository.save(doc1);
            log.info("Documento criado para veículo: {} - {}", veiculo1.getPlaca(), doc1.getTipoDocumento());

            DocumentoVeiculo doc2 = new DocumentoVeiculo();
            doc2.setVeiculo(veiculo1);
            doc2.setTipoDocumento("IPVA");
            doc2.setAnoReferencia(2024);
            doc2.setStatus("Pago");
            doc2.setDataPagamento(LocalDate.of(2024, 1, 10));
            documentoVeiculoRepository.save(doc2);
            log.info("Documento criado para veículo: {} - {}", veiculo1.getPlaca(), doc2.getTipoDocumento());

            DocumentoVeiculo doc3 = new DocumentoVeiculo();
            doc3.setVeiculo(veiculo2);
            doc3.setTipoDocumento("CRLV - Licenciamento");
            doc3.setAnoReferencia(2024);
            doc3.setStatus("Pago");
            doc3.setDataPagamento(LocalDate.of(2024, 2, 20));
            documentoVeiculoRepository.save(doc3);
            log.info("Documento criado para veículo: {} - {}", veiculo2.getPlaca(), doc3.getTipoDocumento());

            DocumentoVeiculo doc4 = new DocumentoVeiculo();
            doc4.setVeiculo(veiculo3);
            doc4.setTipoDocumento("IPVA");
            doc4.setAnoReferencia(2024);
            doc4.setStatus("Pendente");
            documentoVeiculoRepository.save(doc4);
            log.info("Documento criado para veículo: {} - {}", veiculo3.getPlaca(), doc4.getTipoDocumento());

            log.info("Seed do banco de dados concluído com sucesso!");
        };
    }
}
