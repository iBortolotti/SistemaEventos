# Instruções de Compilação e Execução

## 🚀 Execução Rápida (Sem Maven)

### 1. Compilar o projeto

```cmd
cd "e:\Faculdade\Usjt\PSC-Programação de Soluções Computacionais\Pratique\demo\src\main\java"
javac -cp . com\example\*.java com\example\model\*.java com\example\controller\*.java com\example\view\*.java
```

### 2. Executar o sistema

```cmd
java com.example.App
```

## 📝 Alternativa com Maven (Se instalado)

### 1. Compilar com Maven

```cmd
cd "e:\Faculdade\Usjt\PSC-Programação de Soluções Computacionais\Pratique\demo"
mvn clean compile
```

### 2. Executar com Maven

```cmd
mvn exec:java
```

## 🎯 Primeiro Uso

1. Execute o sistema
2. Escolha "Cadastrar Novo Usuário"
3. Preencha seus dados
4. Faça login com seu email
5. Comece a usar o sistema!

## 📋 Funcionalidades Principais

- **Cadastro de usuários** com validação
- **Login/logout** por email
- **Cadastro de eventos** com categorias
- **Participação em eventos**
- **Busca e filtros** de eventos
- **Persistência automática** de dados

## ⚙️ Requisitos

- Java 11+ instalado
- Terminal/Prompt de comando

Consulte o README.md para informações detalhadas sobre o projeto.