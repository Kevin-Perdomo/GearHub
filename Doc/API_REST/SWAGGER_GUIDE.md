# 📚 Guia do Swagger UI - GearHub

Este guia mostra como usar o Swagger UI para testar os endpoints da API, especialmente os endpoints de seed do banco de dados.

## 🌐 Acessando o Swagger UI

1. **Certifique-se de que a aplicação está rodando:**

   ```bash
   ./mvnw spring-boot:run
   ```

2. **Acesse o Swagger UI no navegador:**
   ```
   http://localhost:8080/swagger-ui.html
   ```

## 🌱 Usando os Endpoints de Seed

### Popular o Banco de Dados

#### Passo 1: Localize a seção "Seed"

- Na interface do Swagger, você verá uma lista de controladores
- Procure pela seção **"Seed"** com a descrição: _"Endpoints para popular e limpar o banco de dados. Similar ao comando 'sail seed' do Laravel."_

#### Passo 2: Expanda o endpoint de execução

- Clique em **"POST /seed/api/executar"**
- Você verá o título: **"🌱 Popular Banco de Dados"**
- Leia a descrição completa que explica o que será criado

#### Passo 3: Execute o seed

1. Clique no botão **"Try it out"** (no canto superior direito)
2. Clique no botão **"Execute"** (aparecerá após o Try it out)
3. Aguarde a resposta

#### Passo 4: Veja o resultado

Na seção **"Responses"**, você verá:

- **Code:** `200` (sucesso)
- **Response body:** Mensagem de confirmação
  ```json
  "Seed executado com sucesso! 1 Empresa, 1 Sede, 3 Veículos e 4 Documentos criados."
  ```
  ou
  ```json
  "Banco de dados já contém dados. Nenhuma ação foi realizada."
  ```

### Limpar o Banco de Dados

#### Passo 1: Localize o endpoint de limpeza

- Na seção **"Seed"**, clique em **"POST /seed/api/limpar"**
- Título: **"🧹 Limpar Banco de Dados"**
- ⚠️ **ATENÇÃO:** Esta operação remove TODOS os dados!

#### Passo 2: Execute a limpeza

1. Clique em **"Try it out"**
2. Clique em **"Execute"**
3. Aguarde a confirmação

#### Passo 3: Veja o resultado

- **Response body:**
  ```json
  "Banco de dados limpo com sucesso!"
  ```

## 🔄 Workflow Recomendado no Swagger

### Cenário 1: Popular um banco vazio

```
1. Acesse http://localhost:8080/swagger-ui.html
2. Seção "Seed" → POST /seed/api/executar
3. Try it out → Execute
4. ✅ Banco populado!
```

### Cenário 2: Re-popular o banco (limpar e popular novamente)

```
1. Seção "Seed" → POST /seed/api/limpar
2. Try it out → Execute
3. ✅ Banco limpo!

4. Seção "Seed" → POST /seed/api/executar
5. Try it out → Execute
6. ✅ Banco populado novamente!
```

## 📊 Informações Adicionais no Swagger

### Dados Criados pelo Seed

O Swagger documenta exatamente o que é criado:

**1 Empresa:**

- Nome: GearHub Transportes
- CNPJ: 12.345.678/0001-99

**1 Sede:**

- Nome: Sede Central
- Endereço: Av. Paulista, 1000 - São Paulo/SP

**3 Veículos:**

- Fiat Fiorino (ABC-1234) - 2020 - Gasolina
- Mercedes-Benz Sprinter (XYZ-5678) - 2022 - Diesel
- Renault Master (DEF-9012) - 2021 - Diesel

**4 Documentos:**

- CRLV e IPVA para os veículos

### Proteção Contra Duplicação

O endpoint de seed verifica automaticamente se já existem dados:

- ✅ Se o banco estiver vazio → Popula com dados
- ⚠️ Se já houver dados → Retorna mensagem sem fazer nada

## 🎯 Vantagens do Swagger UI

### 1. Interface Visual

- Não precisa usar terminal ou Postman
- Tudo no navegador, de forma intuitiva

### 2. Documentação Integrada

- Cada endpoint tem descrição detalhada
- Vê exemplos de respostas
- Entende os códigos HTTP (200, 400, 404, etc.)

### 3. Teste Imediato

- Executa requisições com um clique
- Vê a resposta em tempo real
- Não precisa escrever código

### 4. Exploração da API

- Descobre todos os endpoints disponíveis
- Vê os modelos de dados (schemas)
- Entende a estrutura completa da aplicação

## 🔗 URLs Úteis

| Recurso            | URL                                   |
| ------------------ | ------------------------------------- |
| **Swagger UI**     | http://localhost:8080/swagger-ui.html |
| **OpenAPI JSON**   | http://localhost:8080/v3/api-docs     |
| **Aplicação Web**  | http://localhost:8080                 |
| **Seed via GET**   | http://localhost:8080/seed/executar   |
| **Limpar via GET** | http://localhost:8080/seed/limpar     |

## 💡 Dicas

1. **Favoritos:** Adicione `http://localhost:8080/swagger-ui.html` aos favoritos do navegador

2. **Múltiplas abas:** Abra o Swagger em uma aba e a aplicação web em outra para ver as mudanças em tempo real

3. **F12 (DevTools):** Abra as ferramentas de desenvolvedor do navegador para ver as requisições HTTP sendo feitas

4. **Ctrl+R:** Recarregue a página da aplicação após executar seed para ver os novos dados

5. **Documentação sempre atualizada:** O Swagger é gerado automaticamente do código, então está sempre sincronizado

## 🆚 Comparação: Swagger vs Terminal

| Aspecto      | Terminal (curl)          | Swagger UI             |
| ------------ | ------------------------ | ---------------------- |
| Interface    | Linha de comando         | Interface gráfica      |
| Facilidade   | Requer conhecer comandos | Clique em botões       |
| Visualização | Texto puro               | Formatado e colorido   |
| Documentação | Separada                 | Integrada              |
| Teste rápido | `curl -X POST ...`       | Click → Execute        |
| Melhor para  | Scripts/CI/CD            | Desenvolvimento/Testes |

## 📖 Próximos Passos

Depois de dominar o seed pelo Swagger, explore outros endpoints:

- **Empresas:** Criar, listar, editar, excluir empresas
- **Sedes:** Gerenciar sedes vinculadas às empresas
- **Veículos:** CRUD completo de veículos
- **Documentos:** Gerenciar documentação dos veículos

---

**Dica Final:** O Swagger UI é sua melhor ferramenta para desenvolvimento e testes da API! Use e abuse! 🚀
