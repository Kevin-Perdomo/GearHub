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

- **1 Empresa**: GearHub Transportes (CNPJ: 12.345.678/0001-99)
- **1 Sede**: Sede Central (Av. Paulista, 1000 - São Paulo/SP)
- **3 Veículos**:
  - Fiat Fiorino (ABC-1234) - 2020
  - Mercedes-Benz Sprinter (XYZ-5678) - 2022
  - Renault Master (DEF-9012) - 2021
- **4 Documentos**: IPVA e Licenciamento para os veículos

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
