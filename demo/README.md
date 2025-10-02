# Sistema de Eventos 🎉

## Descrição

O **Sistema de Eventos** é uma aplicação Java desenvolvida para facilitar o cadastro e gerenciamento de eventos em uma cidade. O sistema permite que usuários se cadastrem, criem eventos, participem de eventos de outros usuários e acompanhem suas participações de forma organizada.

Este projeto foi desenvolvido como parte da disciplina de **PSC - Programação de Soluções Computacionais** da USJT, seguindo os princípios da programação orientada a objetos e boas práticas de desenvolvimento.

## 🚀 Funcionalidades

### Gestão de Usuários
- ✅ **Cadastro de usuários** com validação de dados
- ✅ **Sistema de login/logout** baseado em email
- ✅ **Edição de perfil** com atualização de informações
- ✅ **Exclusão de conta** com confirmação de segurança
- ✅ **Validação de email, telefone e idade**

### Gestão de Eventos
- ✅ **Cadastro de eventos** com categorias predefinidas
- ✅ **Listagem ordenada** por data e horário
- ✅ **Busca por nome** ou categoria
- ✅ **Filtros avançados**: próximos, passados, atuais
- ✅ **Participação e cancelamento** de participação
- ✅ **Visualização detalhada** de cada evento

### Persistência e Dados
- ✅ **Salvamento automático** em arquivos `.data`
- ✅ **Carregamento automático** na inicialização
- ✅ **Backup de segurança** em caso de erro
- ✅ **Tratamento de exceções** robusto

### Interface e Usabilidade
- ✅ **Interface console** amigável e intuitiva
- ✅ **Menus organizados** com navegação clara
- ✅ **Validações em tempo real** de entrada
- ✅ **Mensagens informativas** e de erro
- ✅ **Estatísticas do sistema** detalhadas

## 🏗️ Arquitetura

O projeto segue o padrão **MVC (Model-View-Controller)** com a seguinte estrutura:

```
src/main/java/com/example/
├── model/                  # Modelos de dados
│   ├── Usuario.java       # Representa um usuário do sistema
│   ├── Evento.java        # Representa um evento
│   └── Categoria.java     # Enum com categorias de eventos
├── controller/            # Controladores (lógica de negócio)
│   ├── UsuarioController.java    # Gerencia usuários
│   └── EventoController.java     # Gerencia eventos
├── view/                  # Interface do usuário
│   └── MenuPrincipal.java # Menus e interação com usuário
└── App.java              # Classe principal
```

### Categorias de Eventos Disponíveis

1. **Festa** - Aniversários, formaturas, confraternizações
2. **Evento Esportivo** - Jogos, campeonatos, corridas
3. **Show** - Apresentações musicais e artísticas
4. **Cultural** - Teatro, exposições, eventos literários
5. **Gastronômico** - Festivais de comida, degustações
6. **Educacional** - Palestras, workshops, seminários
7. **Corporativo** - Reuniões, convenções, networking
8. **Outros** - Eventos que não se encaixam nas categorias acima

## 🛠️ Tecnologias Utilizadas

- **Java 11+** - Linguagem de programação principal
- **Maven** - Gerenciamento de dependências e build
- **Serialização Java** - Persistência de dados em arquivos
- **LocalDateTime** - Gerenciamento de datas e horários
- **Streams API** - Processamento de coleções
- **Scanner** - Entrada de dados via console

## 📋 Pré-requisitos

- **JDK 11** ou superior instalado
- **Maven** instalado (opcional, pode usar o wrapper incluído)
- **IDE** recomendada: VS Code, IntelliJ IDEA ou Eclipse

## 🚀 Como Executar

### 1. Via Maven (Recomendado)

```bash
# Clone ou baixe o projeto
cd demo

# Compile o projeto
mvn clean compile

# Execute a aplicação
mvn exec:java
```

### 2. Via IDE

1. Abra o projeto em sua IDE preferida
2. Configure o JDK 11+ como versão do projeto
3. Execute a classe `App.java` como aplicação Java

### 3. Via Terminal (Compilação Manual)

```bash
# Navegue até o diretório src
cd src/main/java

# Compile todos os arquivos Java
javac -cp . com/example/*.java com/example/*/*.java

# Execute a aplicação
java com.example.App
```

## 📖 Como Usar

### Primeiro Acesso

1. **Inicie o sistema** - Execute o comando de sua preferência
2. **Cadastre-se** - Escolha a opção "Cadastrar Novo Usuário"
3. **Preencha seus dados**:
   - Nome completo
   - Email válido (será usado para login)
   - Telefone com DDD
   - Cidade onde reside
   - Idade (entre 13 e 120 anos)
4. **Faça login** com o email cadastrado

### Usando o Sistema

#### Cadastrando um Evento
1. No menu principal, escolha "📅 Cadastrar Evento"
2. Preencha as informações:
   - Nome do evento
   - Endereço completo
   - Categoria (escolha uma das opções)
   - Data e hora (formato: dd/MM/yyyy HH:mm)
   - Descrição detalhada

#### Participando de Eventos
1. Escolha "✅ Participar de Evento"
2. Veja a lista de eventos próximos
3. Selecione o evento desejado
4. Confirme sua participação

#### Gerenciando suas Participações
1. Use "📋 Meus Eventos" para ver eventos que você participa
2. Use "❌ Cancelar Participação" para se retirar de eventos

#### Buscando Eventos
1. Escolha "🔍 Buscar Eventos"
2. Selecione o tipo de busca:
   - Por nome
   - Por categoria
   - Eventos próximos
   - Eventos passados
   - Eventos acontecendo agora

## 💾 Arquivos de Dados

O sistema cria automaticamente dois arquivos para persistência:

- **`events.data`** - Armazena todos os eventos cadastrados
- **`usuarios.data`** - Armazena todos os usuários cadastrados

> ⚠️ **Importante**: Não delete esses arquivos se quiser manter seus dados!

## 🧪 Conceitos de Programação Demonstrados

### Orientação a Objetos
- **Encapsulamento**: Atributos privados com getters/setters
- **Herança**: Implementação de interfaces como `Serializable`
- **Polimorfismo**: Sobrescrita de métodos como `toString()`, `equals()`, `hashCode()`
- **Abstração**: Separação clara entre Model, View e Controller

### Estruturas de Dados
- **ArrayList**: Armazenamento dinâmico de usuários e eventos
- **Streams**: Filtragem e ordenação de dados
- **Optional**: Tratamento seguro de valores que podem ser nulos
- **Enum**: Definição de categorias de eventos

### Tratamento de Exceções
- **Try-catch**: Captura e tratamento de erros
- **IOException**: Tratamento de erros de arquivo
- **ClassNotFoundException**: Tratamento de erros de serialização
- **NumberFormatException**: Validação de entrada numérica

### Validações
- **Email**: Validação com regex
- **Telefone**: Validação de formato brasileiro
- **Data**: Validação de formato e datas futuras
- **Idade**: Validação de faixa etária

## 📊 Estatísticas e Relatórios

O sistema oferece estatísticas detalhadas:

### Eventos
- Total de eventos cadastrados
- Eventos próximos, passados e atuais
- Distribuição por categoria
- Número de participantes por evento

### Usuários
- Total de usuários cadastrados
- Distribuição por cidade
- Distribuição por faixa etária

## 🔧 Estrutura de Classes Detalhada

### Modelo (Model)

#### `Usuario.java`
```java
- String nome
- String email
- String telefone
- String cidade
- int idade
+ métodos de validação
+ equals/hashCode baseado em email
```

#### `Evento.java`
```java
- long id (gerado automaticamente)
- String nome
- String endereco
- Categoria categoria
- LocalDateTime horario
- String descricao
- List<Usuario> participantes
+ métodos de gerenciamento de participantes
+ verificações de status (passado, atual, próximo)
```

#### `Categoria.java` (Enum)
```java
FESTA, EVENTO_ESPORTIVO, SHOW, CULTURAL, 
GASTRONOMICO, EDUCACIONAL, CORPORATIVO, OUTROS
+ métodos de busca e listagem
```

### Controlador (Controller)

#### `UsuarioController.java`
- Gerenciamento de usuários (CRUD)
- Sistema de login/logout
- Validações de dados
- Persistência em arquivo

#### `EventoController.java`
- Gerenciamento de eventos (CRUD)
- Filtragem e ordenação
- Gerenciamento de participantes
- Persistência em arquivo

### Visualização (View)

#### `MenuPrincipal.java`
- Interface console completa
- Menus organizados
- Validações de entrada
- Formatação de saída

## 🤝 Contribuições

Este é um projeto educacional, mas contribuições são bem-vindas! 

### Como contribuir:
1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Melhorias Futuras

### Funcionalidades Planejadas
- [ ] Interface gráfica (JavaFX ou Swing)
- [ ] Banco de dados (MySQL/PostgreSQL)
- [ ] Notificações por email
- [ ] Sistema de avaliação de eventos
- [ ] Fotos de eventos
- [ ] Mapa com localização dos eventos
- [ ] Sistema de amigos/seguidores
- [ ] Chat entre participantes
- [ ] Integração com redes sociais

### Melhorias Técnicas
- [ ] Testes unitários (JUnit)
- [ ] Logging (Log4j)
- [ ] Configuração externa (Properties)
- [ ] API REST (Spring Boot)
- [ ] Dockerização
- [ ] CI/CD (GitHub Actions)

## 🐛 Problemas Conhecidos

- A limpeza de tela pode não funcionar em todos os sistemas operacionais
- Alguns caracteres especiais podem não ser exibidos corretamente em alguns terminais
- O sistema não valida se um endereço realmente existe

## 📄 Licença

Este projeto é desenvolvido para fins educacionais como parte do curso de Programação de Soluções Computacionais da USJT.

## 👨‍💻 Autores

- **Sistema de Eventos** - Projeto PSC USJT
- **Orientação** - Professor da disciplina PSC

---

## 📞 Suporte

Se você encontrar problemas ou tiver dúvidas:

1. Verifique se todos os pré-requisitos estão instalados
2. Certifique-se de estar usando Java 11+
3. Consulte os logs de erro para mais informações
4. Revise este README para instruções detalhadas

---

**Desenvolvido com ❤️ para aprendizado de Java e Programação Orientada a Objetos**