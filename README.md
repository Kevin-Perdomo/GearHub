# 🚗 GearHub

Sistema de gerenciamento de veículos para empresas e famílias. Organiza veículos por empresa/sede ou casa, registra manutenções preventivas e corretivas, armazena documentação e oferece um painel central para acompanhar todas as informações.

## 📋 Modelo de Dados

### Estrutura de Tabelas

```
Empresa
-------
id (PK)
nome
cnpj (ou cpf)
data_criacao

Sede
----
id (PK)
nome
endereco
empresa_id (FK → Empresa.id)

Veiculo
-------
id (PK)
modelo
placa
ano
sede_id (FK → Sede.id)
descricao

Manutencao
----------
id (PK)
tipo (ex: troca de óleo, pneus, revisão)
data_realizada
data_proxima
descricao
veiculo_id (FK → Veiculo.id)

Documento
---------
id (PK)
tipo (manual, CRLV, nota fiscal, etc.)
arquivo_path
veiculo_id (FK → Veiculo.id)
descricao
data_upload
```

## 🛠️ Tecnologias

- **Backend:** Spring Boot 3.5.6
- **Banco de Dados:** MySQL 8.0
- **ORM:** Spring Data JPA / Hibernate
- **Visualização:** Thymeleaf
- **Containerização:** Docker & Docker Compose
- **Build:** Maven
- **Java:** 17

## 🚀 Como Executar

### Pré-requisitos

- Docker e Docker Compose instalados
- Ou: Java 17 + Maven + MySQL

### Opção 1: Com Docker (Recomendado)

```bash
# Clone o repositório
git clone https://github.com/Kevin-Perdomo/GearHub.git
cd GearHub

# Inicie os containers
docker-compose up --build

# Acesse a aplicação
# http://localhost:8080
```

### Opção 2: Execução Local

```bash
# 1. Certifique-se de que o MySQL está rodando
# 2. Configure o application.properties se necessário

# 3. Compile e execute
./mvnw clean install
./mvnw spring-boot:run

# Acesse a aplicação
# http://localhost:8080
```

## 📦 Estrutura do Projeto

```
GearHub/
├── src/
│   ├── main/
│   │   ├── java/com/gearhub/
│   │   │   ├── model/          # Entidades JPA
│   │   │   ├── repository/     # Repositories
│   │   │   └── GearHubApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/         # CSS, JS, imagens
│   │       └── templates/      # Views Thymeleaf
│   └── test/
├── Doc/                        # Documentação
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

## 🔧 Configuração

### application.properties

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/gearhub
spring.datasource.username=root
spring.datasource.password=root

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Docker Compose

O `docker-compose.yml` configura:

- MySQL 8.0 na porta 3306
- Aplicação Spring Boot na porta 8080
- Volume persistente para dados do MySQL

## 📚 API REST (Em Desenvolvimento)

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs:** http://localhost:8080/api-docs

## 🎯 Funcionalidades Planejadas

- ✅ Cadastro de Empresas e Sedes
- ✅ Gestão de Veículos
- ✅ Registro de Manutenções
- ✅ Upload e Gerenciamento de Documentos
- 🔄 Painel de Controle (Dashboard)
- 🔄 Notificações de Manutenção
- 🔄 Relatórios e Estatísticas
- 🔄 Autenticação e Autorização

## 👥 Contribuição

Contribuições são bem-vindas! Por favor, abra uma issue ou pull request.

## 📄 Licença

Este projeto está sob a licença especificada no arquivo [LICENSE](LICENSE).

## 📧 Contato

Kevin Perdomo - [@Kevin-Perdomo](https://github.com/Kevin-Perdomo)

---

**GearHub** - Sistema de Gerenciamento de Veículos 🚗🔧
