# Guia de Segurança - GearHub

## Configuração de Variáveis de Ambiente

### 1. Arquivo `.env`

O arquivo `.env` contém dados sensíveis e **NÃO deve ser commitado** no Git. Ele já está incluído no `.gitignore`.

### 2. Como Configurar

1. Copie o arquivo de exemplo:

```bash
cp .env.example .env
```

2. Edite o arquivo `.env` com suas credenciais:

```properties
# MySQL Configuration
MYSQL_ROOT_PASSWORD=SuaSenhaSuperSegura123!
MYSQL_USER=gearhub_user
MYSQL_PASSWORD=OutraSenhaSegura456!
MYSQL_DATABASE=gearhub
MYSQL_CHARACTER_SET_SERVER=utf8mb4
MYSQL_COLLATION_SERVER=utf8mb4_unicode_ci

# Ports
MYSQL_PORT=3306
APP_PORT=8080
```

### 3. Melhores Práticas

#### Senhas Fortes

- Mínimo de 12 caracteres
- Combine letras maiúsculas, minúsculas, números e símbolos
- Não use senhas óbvias como "root", "admin", "123456"

#### Usuário Dedicado

Recomenda-se criar um usuário específico para a aplicação ao invés de usar `root`:

```properties
MYSQL_USER=gearhub_user
MYSQL_PASSWORD=senha_forte_aqui
```

O Docker criará automaticamente esse usuário com permissões no banco `gearhub`.

#### application.properties

O arquivo `application.properties` agora usa variáveis de ambiente:

```properties
spring.datasource.username=${MYSQL_USER:root}
spring.datasource.password=${MYSQL_PASSWORD:root}
```

Os valores após `:` são valores padrão (fallback) caso as variáveis não existam.

### 4. Execução Local vs Docker

#### Desenvolvimento Local (Spring Boot rodando fora do Docker)

As variáveis do `.env` precisam estar disponíveis no seu ambiente. Você pode:

**Opção 1: Exportar no terminal**

```bash
export MYSQL_USER=gearhub_user
export MYSQL_PASSWORD=sua_senha
export MYSQL_DATABASE=gearhub
export MYSQL_PORT=3306
```

**Opção 2: Usar IntelliJ/VS Code**

- IntelliJ: Run > Edit Configurations > Environment Variables
- VS Code: Adicione no `launch.json`

**Opção 3: Usar direnv**

```bash
sudo apt install direnv  # ou brew install direnv no Mac
echo 'eval "$(direnv hook zsh)"' >> ~/.zshrc
direnv allow .
```

#### Docker Compose

O Docker Compose carrega automaticamente as variáveis do arquivo `.env`.

### 5. Checklist de Segurança

- [ ] `.env` está no `.gitignore`
- [ ] `.env` não foi commitado no repositório
- [ ] Senhas fortes foram configuradas
- [ ] Usuário dedicado foi criado (opcional mas recomendado)
- [ ] `.env.example` não contém dados sensíveis reais
- [ ] Credenciais de produção são diferentes das de desenvolvimento

### 6. Auditoria de Segurança

Verifique se você commitou acidentalmente dados sensíveis:

```bash
# Procurar por possíveis senhas no histórico
git log --all --full-history --source -- .env

# Verificar se .env está sendo ignorado
git check-ignore .env
```

Se você commitou acidentalmente o `.env`:

```bash
# CUIDADO: Isso reescreve o histórico do Git
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch .env" \
  --prune-empty --tag-name-filter cat -- --all
```

### 7. Produção

Em produção, **NUNCA** use arquivos `.env` commitados. Use:

- **Docker Secrets** (Docker Swarm)
- **Kubernetes Secrets**
- **Variáveis de ambiente do sistema**
- **Serviços de gerenciamento de secrets** (AWS Secrets Manager, Azure Key Vault, HashiCorp Vault)

### 8. Exemplo de CI/CD

Se usar GitHub Actions, adicione secrets no repositório:

```yaml
# .github/workflows/deploy.yml
env:
  MYSQL_USER: ${{ secrets.MYSQL_USER }}
  MYSQL_PASSWORD: ${{ secrets.MYSQL_PASSWORD }}
```

## Resumo

✅ **FAÇA:**

- Use variáveis de ambiente
- Senhas fortes e únicas
- Mantenha `.env` no `.gitignore`
- Use usuários dedicados

❌ **NÃO FAÇA:**

- Commitar `.env` no Git
- Usar senhas padrão em produção
- Compartilhar credenciais em plain text
- Usar root em produção
