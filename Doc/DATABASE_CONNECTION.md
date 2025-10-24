# Guia de Conexão ao Banco de Dados - GearHub

## Visão Geral

O GearHub usa MySQL 8.0 rodando em Docker. Para conectar ao banco de dados via DBeaver ou qualquer cliente MySQL externo:

⭐ **USE SEMPRE O USUÁRIO DA APLICAÇÃO** (`gearhub_app`)

O usuário ROOT está disponível apenas **dentro do container Docker** por questões de segurança. Para acesso externo (DBeaver, MySQL Workbench, etc.), use o usuário da aplicação.

## Credenciais

### Onde encontrar as senhas?

As credenciais estão no arquivo `.env` na raiz do projeto (**este arquivo não deve ser commitado**):

```properties
# MySQL Root (Super usuário)
MYSQL_ROOT_PASSWORD=sua_senha_root

# Usuário da Aplicação (Acesso limitado)
MYSQL_USER=gearhub_app
MYSQL_PASSWORD=sua_senha_do_usuario

# Base de dados
MYSQL_DATABASE=gearhub

# Porta
MYSQL_PORT=3306
```

## Conexão com DBeaver

### 1. Criar Nova Conexão

1. Abra o DBeaver
2. Clique em **Database** > **New Database Connection**
3. Selecione **MySQL 8+**
4. Clique em **Next**

### 2. Configurar Conexão (Usuário da Aplicação)

**⭐ Esta é a única forma de conectar externamente ao MySQL no Docker:**

```
Host: localhost
Port: 3306
Database: gearhub
Username: gearhub_app
Password: GearHub@2025!User (ou o valor de MYSQL_PASSWORD do seu .env)
```

**Por que não usar ROOT?**

O usuário ROOT no MySQL Docker está configurado para aceitar conexões **apenas de dentro do container** por segurança. Para administração via ROOT, você precisa entrar no container (veja seção "Conexão via Terminal").

### 3. Testar Conexão

1. Clique em **Test Connection**
2. Se aparecer erro de driver:
   - Clique em **Download** para baixar o driver MySQL
   - Aguarde o download completar
   - Clique novamente em **Test Connection**
3. Se aparecer "Connected", clique em **Finish**

### 4. Configurações Avançadas (Opcional)

Na aba **Driver properties**, você pode adicionar:

```
allowPublicKeyRetrieval=true
useSSL=false
serverTimezone=UTC
```

Essas propriedades já estão configuradas na aplicação Spring Boot.

## Conexão via Terminal (MySQL CLI)

### ⭐ Acesso ROOT (Apenas dentro do container)

Para usar o ROOT, você **deve** entrar no container Docker:

```bash
# Conectar como ROOT (de dentro do container)
docker exec -it gearhub-mysql mysql -u root -p
# Digite a senha: GearHub@2025!Root (ou seu MYSQL_ROOT_PASSWORD)
```

### ✅ Acesso Usuário da Aplicação (Dentro ou fora do container)

```bash
# Via Docker (recomendado)
docker exec -it gearhub-mysql mysql -u gearhub_app -p gearhub
# Digite a senha: GearHub@2025!User (ou seu MYSQL_PASSWORD)

# Se você tem MySQL Client instalado localmente
mysql -h localhost -P 3306 -u gearhub_app -p gearhub
# Digite a senha: GearHub@2025!User
```

## Estrutura do Banco de Dados

### Tabelas Criadas

Após subir a aplicação, o Hibernate cria automaticamente as seguintes tabelas:

```
gearhub
├── GH_EMPRESAS          # Empresas/Famílias
├── GH_SEDES             # Sedes/Localizações
├── GH_VEICULOS          # Veículos
├── GH_DOCUMENTOS        # Documentos dos veículos (PDF em BLOB)
├── GH_BATERIAS          # Histórico de baterias (fotos em BLOB)
├── GH_PNEUS             # Histórico de pneus (fotos em BLOB)
└── GH_OLEOS             # Histórico de trocas de óleo (fotos em BLOB)
```

### Convenções de Nomenclatura

- **Tabelas**: Prefixo `GH_`, nomes em MAIÚSCULAS
- **Colunas**: Nomes em MAIÚSCULAS com underscore (SNAKE_CASE)
- **Chaves Estrangeiras**: Sufixo `_ID`
- **Arquivos**: Armazenados como BLOB (binário) na tabela correspondente

## Queries Úteis

### Ver todas as tabelas

```sql
SHOW TABLES;
```

### Verificar estrutura de uma tabela

```sql
DESCRIBE GH_EMPRESAS;
DESCRIBE GH_VEICULOS;
```

### Contar registros

```sql
SELECT COUNT(*) FROM GH_EMPRESAS;
SELECT COUNT(*) FROM GH_VEICULOS;
SELECT COUNT(*) FROM GH_DOCUMENTOS;
```

### Ver dados com relacionamentos

```sql
-- Veículos com suas sedes e empresas
SELECT
    v.MODELO,
    v.PLACA,
    s.NOME as SEDE,
    e.NOME as EMPRESA
FROM GH_VEICULOS v
JOIN GH_SEDES s ON v.SEDE_ID = s.ID
JOIN GH_EMPRESAS e ON s.EMPRESA_ID = e.ID;
```

### Verificar tamanho dos BLOBs

```sql
-- Documentos com tamanho do PDF
SELECT
    ID,
    NOME_ARQUIVO,
    TIPO_DOCUMENTO,
    LENGTH(ARQUIVO_PDF) as TAMANHO_BYTES,
    ROUND(LENGTH(ARQUIVO_PDF) / 1024, 2) as TAMANHO_KB
FROM GH_DOCUMENTOS
WHERE ARQUIVO_PDF IS NOT NULL;
```

## Troubleshooting

### "Access denied for user"

**Problema**: Senha incorreta ou usuário não existe

**Solução**:

1. Verifique o arquivo `.env`
2. Certifique-se de que o usuário foi criado (usuário da aplicação só existe se `MYSQL_USER` e `MYSQL_PASSWORD` estiverem definidos no `.env`)
3. Recrie os containers se mudou as credenciais:
   ```bash
   docker compose down
   sudo rm -rf mysql-data
   docker compose up -d
   ```

### "Can't connect to MySQL server"

**Problema**: MySQL não está rodando ou porta errada

**Solução**:

1. Verifique se o container está rodando:
   ```bash
   docker ps
   ```
2. Se não estiver, suba os containers:
   ```bash
   docker compose up -d
   ```
3. Verifique a porta no `.env` (padrão: 3306)

### "Public Key Retrieval is not allowed"

**Problema**: Configuração de segurança do driver

**Solução**: Adicione nas propriedades da conexão do DBeaver:

```
allowPublicKeyRetrieval=true
```

## Diferenças entre ROOT e Usuário da Aplicação

### Usuário ROOT

**Permissões:**

- ✅ Acesso a TODAS as bases de dados (mysql, information_schema, performance_schema, sys, gearhub)
- ✅ Criar/deletar bases de dados
- ✅ Criar/deletar usuários
- ✅ Modificar permissões
- ✅ Fazer backup completo do servidor

**Quando usar:**

- Setup inicial
- Administração do servidor
- Backup/restore completo
- Troubleshooting profundo

### Usuário da Aplicação (gearhub_app)

**Permissões:**

- ✅ Acesso APENAS à base `gearhub`
- ✅ SELECT, INSERT, UPDATE, DELETE em todas as tabelas de `gearhub`
- ✅ CREATE, DROP, ALTER (para migrations)
- ❌ Não pode criar outras bases de dados
- ❌ Não pode criar usuários
- ❌ Não pode acessar bases do sistema

**Quando usar:**

- Desenvolvimento diário
- Queries e análise de dados
- Testes de features
- Segurança (princípio do menor privilégio)

## Exemplo Prático

### Cenário 1: Quero apenas visualizar dados do GearHub

✅ **Use: Usuário da Aplicação (`gearhub_app`)**

```
Username: gearhub_app
Password: [sua MYSQL_PASSWORD]
Database: gearhub
```

### Cenário 2: Preciso criar um backup do banco

✅ **Use: ROOT**

```bash
docker exec gearhub-mysql mysqldump -u root -p[MYSQL_ROOT_PASSWORD] gearhub > backup.sql
```

### Cenário 3: Quero resetar completamente o banco

✅ **Use: ROOT ou recriar containers**

Opção 1 - Via ROOT:

```sql
DROP DATABASE gearhub;
CREATE DATABASE gearhub;
```

Opção 2 - Recriar containers (recomendado):

```bash
docker compose down
sudo rm -rf mysql-data
docker compose up -d
```

## Segurança

### ⚠️ IMPORTANTE

1. **NUNCA commite o arquivo `.env`** (já está no `.gitignore`)
2. **Use senhas fortes** em produção (mínimo 16 caracteres)
3. **Mude as senhas padrão** antes de fazer deploy
4. **Use o usuário da aplicação** para desenvolvimento diário
5. **Restrinja o acesso ao ROOT** apenas quando necessário

### Exemplo de senhas fortes

```properties
MYSQL_ROOT_PASSWORD=Gh@2025!R00t#Secure$123
MYSQL_PASSWORD=GhApp@2025!User#Password$456
```

## Resumo Rápido

### Para DBeaver / MySQL Workbench / Qualquer cliente externo:

```
✅ USE ESTE:
Username: gearhub_app
Password: GearHub@2025!User (MYSQL_PASSWORD do .env)
Host: localhost
Port: 3306
Database: gearhub
```

### Para administração ROOT:

```
⚠️ Apenas via Docker:
docker exec -it gearhub-mysql mysql -u root -p
(Digite: GearHub@2025!Root quando solicitado)
```

| Aspecto                      | ROOT                  | Usuário da Aplicação |
| ---------------------------- | --------------------- | -------------------- |
| **Username**                 | `root`                | `gearhub_app`        |
| **Senha**                    | `MYSQL_ROOT_PASSWORD` | `MYSQL_PASSWORD`     |
| **Acesso Externo (DBeaver)** | ❌ Não funciona       | ✅ Funciona          |
| **Acesso via Docker**        | ✅ Funciona           | ✅ Funciona          |
| **Acesso ao banco gearhub**  | ✅ Sim                | ✅ Sim               |
| **Recomendado para dev**     | ❌ Não                | ✅ Sim               |

---

**Última atualização**: 24 de outubro de 2025
