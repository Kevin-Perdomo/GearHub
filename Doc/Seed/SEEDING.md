# 🌱 Seed do Banco de Dados - GearHub

Este guia explica como popular (seed) e limpar o banco de dados do GearHub, similar ao comando `sail seed` do Laravel.

## 🚀 Como Popular o Banco (Seed)

Diferente do Laravel onde o seed roda automaticamente no startup, no Spring Boot você pode escolher **quando** popular o banco.

### Opção 1: Via Swagger UI (Interface Gráfica) 🎨

```
http://localhost:8080/swagger-ui.html
```

1. Acesse o Swagger UI
2. Localize a seção **"Seed"**
3. Clique em **"POST /seed/api/executar"**
4. Clique em **"Try it out"**
5. Clique em **"Execute"**
6. Veja a resposta com status de sucesso! ✅

### Opção 2: Via Navegador (mais fácil)

```
http://localhost:8080/seed/executar
```

### Opção 3: Via Terminal (similar ao Laravel)

```bash
curl http://localhost:8080/seed/executar
```

### Opção 4: Via API REST (POST)

```bash
curl -X POST http://localhost:8080/seed/api/executar
```

## 🧹 Como Limpar o Banco

### Via Swagger UI 🎨

```
http://localhost:8080/swagger-ui.html
```

1. Localize **"POST /seed/api/limpar"**
2. Clique em **"Try it out"** → **"Execute"**

### Via Navegador

```
http://localhost:8080/seed/limpar
```

### Via Terminal

```bash
curl http://localhost:8080/seed/limpar
```

### Via API REST (POST)

```bash
curl -X POST http://localhost:8080/seed/api/limpar
```

## 📊 O Que o Seed Cria?

Quando você executa o seed, os seguintes dados são inseridos:

### 🏢 Empresas (2)

1. **GearHub Transportes Ltda** (CNPJ: 12.345.678/0001-99)
2. **LogiFreight Brasil S.A.** (CNPJ: 98.765.432/0001-10)

### 🏭 Sedes (6 - 3 por empresa)

**Empresa 1 - GearHub Transportes:**

- Sede Central - SP (São Paulo)
- Filial Rio de Janeiro (RJ)
- Filial Belo Horizonte (MG)

**Empresa 2 - LogiFreight Brasil:**

- Matriz Curitiba (PR)
- Filial Porto Alegre (RS)
- Filial Florianópolis (SC)

### 🚗 Veículos (24 - 4 por sede)

**Tipos de veículos incluídos:**

- Furgões pequenos (Fiorino, Saveiro, Montana, etc.)
- Vans comerciais (Sprinter, Master, Daily, Boxer, etc.)
- Caminhonetes (Hilux, Amarok, Frontier, L200, S10, Ranger, etc.)
- Diversos modelos de diferentes marcas

**Marcas representadas:**
Fiat, Volkswagen, Chevrolet, Ford, Mercedes-Benz, Renault, Iveco, Peugeot, Citroën, Hyundai, JAC, Kia, Toyota, Nissan, Mitsubishi, Ram, Jeep

### 📄 Documentos (96 - 4 por veículo)

Para cada veículo são criados:

1. **IPVA 2024** - Status: Pago
2. **CRLV - Licenciamento 2024** - Status: Pago
3. **Seguro Veicular 2024** - Status: Pago
4. **Revisão Anual 2024** - Status: Pendente

### 📈 Total Criado

- ✅ **2 Empresas**
- ✅ **6 Sedes**
- ✅ **24 Veículos**
- ✅ **96 Documentos**

## ⚠️ Observações Importantes

1. **Proteção contra duplicação**: O seed verifica se já existem dados antes de executar. Se já houver empresas cadastradas, ele não fará nada.

2. **Ordem de execução**: Se você quiser re-popular o banco, primeiro execute `/seed/limpar` e depois `/seed/executar`.

3. **Banco vazio no startup**: O banco agora sobe vazio por padrão. Você controla quando popular.

## 🔄 Workflow Recomendado

```bash
# 1. Limpar o banco (se necessário)
curl http://localhost:8080/seed/limpar

# 2. Popular com dados de teste
curl http://localhost:8080/seed/executar

# 3. Acessar a aplicação
# http://localhost:8080
```

## 🆚 Comparação com Laravel

| Laravel              | Spring Boot (GearHub)                      |
| -------------------- | ------------------------------------------ |
| `sail seed`          | `curl http://localhost:8080/seed/executar` |
| Roda automaticamente | Manual (você escolhe quando)               |
| Via terminal apenas  | Navegador, Terminal ou **Swagger UI** 🎨   |

## 🎯 Vantagens desta Abordagem

- ✅ **Controle total**: Você decide quando popular o banco
- ✅ **Flexível**: Pode limpar e re-popular quantas vezes quiser
- ✅ **Seguro**: Não popula acidentalmente em produção
- ✅ **Fácil de usar**: Basta acessar uma URL
- ✅ **API REST**: Pode ser integrado com scripts de CI/CD
- ✅ **Swagger UI**: Interface gráfica para testar os endpoints 🎨

## 📚 Documentação da API

Acesse a documentação completa e interativa da API em:

**Swagger UI:**

```
http://localhost:8080/swagger-ui.html
```

**OpenAPI JSON:**

```
http://localhost:8080/v3/api-docs
```

Pelo Swagger você pode:

- 📖 Ver todos os endpoints disponíveis
- 🧪 Testar os endpoints diretamente no navegador
- 📋 Ver exemplos de requisições e respostas
- 🔍 Explorar os modelos de dados

---

**Dica**: Adicione o Swagger UI aos seus favoritos do navegador para acesso rápido à documentação! 🔖
