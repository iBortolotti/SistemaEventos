# 📁 Estrutura do Projeto - Sistema de Eventos

## 🗂️ Organização dos Arquivos

```
📦 Pratique/
├── 📄 .gitignore              # Arquivos ignorados pelo Git
├── 📄 Pratique.txt            # Requisitos originais do projeto
├── 📄 Projeto-Ver1.txt        # ❌ Ignorado pelo Git (rascunho)
├── 📄 Projeto-VerMaven.txt    # ❌ Ignorado pelo Git (rascunho)
├── 📁 .vscode/                # ❌ Ignorado pelo Git (config IDE)
└── 📁 demo/                   # ✅ Projeto principal (versionado)
    ├── 📄 pom.xml             # Configuração Maven
    ├── 📄 README.md           # Documentação principal
    ├── 📄 INSTRUCOES.md       # Guia rápido
    ├── 📄 CORRECAO_EMAIL.md   # Documentação de correções
    ├── 📁 src/main/java/      # Código fonte
    │   └── com/example/
    │       ├── 📄 App.java    # Classe principal
    │       ├── 📁 model/      # Modelos de dados
    │       ├── 📁 controller/ # Lógica de negócio
    │       └── 📁 view/       # Interface do usuário
    ├── 📁 src/test/java/      # Testes unitários
    └── 📁 target/             # ❌ Ignorado pelo Git (build)
```

## 🚫 Arquivos Ignorados pelo Git

### Arquivos de Documentação Temporária
- `Projeto-Ver1.txt` - Rascunho inicial do projeto
- `Projeto-VerMaven.txt` - Documentação da versão Maven

### Configurações de IDE
- `.vscode/` - Configurações do Visual Studio Code
- `.idea/` - Configurações do IntelliJ IDEA
- Arquivos de configuração específicos de editores

### Arquivos de Build e Compilação
- `target/` - Diretório de build do Maven
- `*.class` - Arquivos Java compilados
- `*.jar`, `*.war` - Arquivos empacotados

### Dados da Aplicação
- `events.data` - Arquivo de persistência de eventos
- `usuarios.data` - Arquivo de persistência de usuários
- `*.log` - Arquivos de log

### Sistema Operacional
- `Thumbs.db` - Cache de miniaturas do Windows
- `.DS_Store` - Metadados do macOS
- Arquivos temporários do sistema

## ✅ Arquivos Versionados

### Código Fonte
- Todas as classes Java em `src/main/java/`
- Testes em `src/test/java/`
- Configuração Maven (`pom.xml`)

### Documentação
- `README.md` - Documentação principal
- `INSTRUCOES.md` - Guia de uso rápido
- `CORRECAO_EMAIL.md` - Documentação de correções
- `Pratique.txt` - Requisitos originais

### Configuração
- `.gitignore` - Regras de versionamento
- Estrutura de diretórios Maven

## 🔧 Comandos Git Úteis

### Verificar Status
```bash
git status
```

### Adicionar Novos Arquivos
```bash
git add <arquivo>
git add .  # Adiciona todos os arquivos não ignorados
```

### Fazer Commit
```bash
git commit -m "Mensagem descritiva"
```

### Ver Histórico
```bash
git log --oneline
```

### Ver Arquivos Ignorados
```bash
git status --ignored
```

## 📋 Boas Práticas

### ✅ O que Versionar
- Código fonte (.java)
- Documentação (.md, .txt requisitos)
- Configuração (pom.xml)
- Scripts de build
- Testes unitários

### ❌ O que NÃO Versionar
- Arquivos compilados (.class, .jar)
- Diretórios de build (target/)
- Configurações de IDE pessoais
- Dados da aplicação (*.data)
- Logs e arquivos temporários
- Rascunhos e documentação temporária

## 🎯 Próximos Passos

1. **Desenvolvimento**: Continue adicionando funcionalidades
2. **Commits Frequentes**: Faça commits pequenos e descritivos
3. **Branches**: Use branches para novas funcionalidades
4. **Tags**: Marque versões importantes

## 📝 Mensagens de Commit Sugeridas

```bash
# Para novas funcionalidades
git commit -m "feat: adiciona busca por data nos eventos"

# Para correções
git commit -m "fix: corrige validação de email no cadastro"

# Para documentação
git commit -m "docs: atualiza README com novas instruções"

# Para refatoração
git commit -m "refactor: melhora estrutura do MenuPrincipal"
```

---

**Projeto criado para a disciplina PSC - Programação de Soluções Computacionais da USJT**