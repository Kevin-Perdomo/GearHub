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
- **Template Engine:** Thymeleaf
- **Estilização:** Tailwind CSS
- **API Documentation:** Swagger/OpenAPI 3.0
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

# Crie o arquivo .env baseado no .env.example
cp .env.example .env

# Inicie os containers
docker compose up -d

# Inicie a aplicação Spring Boot
./mvnw spring-boot:run

# Popular o banco de dados (opcional)
curl http://localhost:8080/seed/executar

# Acesse a aplicação
# Web: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
```

### Opção 2: Execução Local

```bash
# 1. Certifique-se de que o MySQL está rodando
# 2. Configure o application.properties se necessário

# 3. Compile e execute
./mvnw clean install
./mvnw spring-boot:run

# 4. Popular o banco (opcional)
curl http://localhost:8080/seed/executar

# Acesse a aplicação
# Web: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
```

## 🌱 Seed do Banco de Dados

Diferente do Laravel (`sail seed`), o GearHub permite que você escolha quando popular o banco:

**Via Swagger UI (Recomendado):**

1. Acesse: http://localhost:8080/swagger-ui.html
2. Localize a seção **"Seed"**
3. Execute **POST /seed/api/executar**

**Via Terminal:**

```bash
# Popular o banco
curl -X POST http://localhost:8080/seed/api/executar

# Limpar o banco
curl -X POST http://localhost:8080/seed/api/limpar
```

**Via Navegador:**

```
http://localhost:8080/seed/executar
http://localhost:8080/seed/limpar
```

📖 **Documentação completa:** [Doc/Seed/SEEDING.md](Doc/Seed/SEEDING.md)

## 📦 Estrutura do Projeto

```
GearHub/
├── src/
│   ├── main/
│   │   ├── java/com/gearhub/
│   │   │   ├── config/         # Configurações (Swagger, Seeder)
│   │   │   ├── controller/     # Controllers REST e Web
│   │   │   ├── model/          # Entidades JPA
│   │   │   ├── repository/     # Repositories
│   │   │   └── GearHubApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/         # CSS, JS, imagens
│   │       └── templates/      # Views Thymeleaf
│   │           ├── empresas/
│   │           ├── sedes/
│   │           ├── veiculos/
│   │           ├── documentos-veiculo/
│   │           ├── paginas/
│   │           ├── layouts/
│   │           └── error/
│   └── test/
├── Doc/
│   ├── API_REST/               # Documentação da API
│   └── Seed/
│       └── SEEDING.md          # Guia do Database Seeder
├── mysql-data/                 # Volume persistente do MySQL
├── docker-compose.yml
├── Dockerfile
├── .env.example
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

# Swagger/OpenAPI
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

### Docker Compose

O `docker-compose.yml` configura:

- MySQL 8.0 na porta 3306
- Volume persistente em `./mysql-data`
- Variáveis de ambiente via arquivo `.env`

## 📚 Documentação da API

### Swagger UI (Interface Interativa)

**URL:** http://localhost:8080/swagger-ui.html

O Swagger UI oferece:

- 📖 Documentação completa de todos os endpoints
- 🧪 Interface para testar endpoints diretamente no navegador
- 📋 Exemplos de requisições e respostas
- 🔍 Exploração dos modelos de dados
- 🌱 **Endpoints de Seed** - Popular e limpar banco de dados

### OpenAPI Specification

**JSON:** http://localhost:8080/v3/api-docs

### Principais Endpoints

#### Seed (Popular/Limpar Banco)

- `POST /seed/api/executar` - Popular banco com dados de teste
- `POST /seed/api/limpar` - Limpar todos os dados

#### Empresas

- `GET /empresas` - Listar todas as empresas
- `GET /empresas/{id}` - Detalhes de uma empresa
- `POST /empresas` - Criar nova empresa
- `PUT /empresas/{id}` - Atualizar empresa
- `DELETE /empresas/{id}` - Excluir empresa

#### Veículos

- `GET /veiculos` - Listar todos os veículos
- `GET /veiculos/{id}` - Detalhes de um veículo
- `POST /veiculos` - Criar novo veículo

_Documentação completa disponível no Swagger UI_

## 🎯 Funcionalidades

### ✅ Implementadas

- Cadastro completo de Empresas e Sedes
- Gestão de Veículos (CRUD completo)
- Registro de Documentos de Veículos
- Interface web responsiva com Tailwind CSS
- **API REST documentada com Swagger/OpenAPI**
- **Database Seeder (popular/limpar banco via API)**
- Docker Compose para ambiente de desenvolvimento
- Volume persistente para dados do MySQL

### 🔄 Em Desenvolvimento

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
