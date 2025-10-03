package com.example.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.example.controller.EventoController;
import com.example.controller.UsuarioController;
import com.example.model.Categoria;
import com.example.model.Evento;
import com.example.model.Usuario;

/**
 * Classe responsável pela interface de usuário do sistema de eventos.
 * Implementa menus e interações via console para todas as funcionalidades.
 * 
 * @author Sistema de Eventos
 * @version 1.0
 */
public class MenuPrincipal {
    
    // Controllers do sistema
    private final EventoController eventoController;
    private final UsuarioController usuarioController;
    
    // Scanner para entrada de dados
    private final Scanner scanner;
    
    // Formatador de data/hora
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    /**
     * Construtor que inicializa os controllers e o scanner
     */
    public MenuPrincipal() {
        this.eventoController = new EventoController();
        this.usuarioController = new UsuarioController();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Inicia o sistema exibindo o menu principal
     */
    public void iniciar() {
        exibirBoasVindas();
        
        // Loop principal do sistema
        boolean continuar = true;
        while (continuar) {
            try {
                if (!usuarioController.temUsuarioLogado()) {
                    continuar = exibirMenuLogin();
                } else {
                    continuar = exibirMenuPrincipal();
                }
            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
                System.out.println("Pressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        // Finaliza o sistema
        finalizarSistema();
    }
    
    /**
     * Exibe mensagem de boas-vindas
     */
    private void exibirBoasVindas() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    SISTEMA DE EVENTOS                        ║");
        System.out.println("║              Cadastro e Notificação de Eventos               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Bem-vindo ao Sistema de Eventos da sua cidade!");
        System.out.println("Aqui você pode descobrir e participar de eventos incríveis.\n");
        pausar();
    }
    
    /**
     * Exibe menu de login/cadastro quando não há usuário logado
     * @return true para continuar, false para sair
     */
    private boolean exibirMenuLogin() {
        limparTela();
        System.out.println("=== ACESSO AO SISTEMA ===\n");
        System.out.println("1. Fazer Login");
        System.out.println("2. Cadastrar Novo Usuário");
        System.out.println("3. Visualizar Eventos (sem login)");
        System.out.println("0. Sair do Sistema");
        System.out.print("\nEscolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                realizarLogin();
                break;
            case 2:
                cadastrarNovoUsuario();
                break;
            case 3:
                visualizarEventosSemLogin();
                break;
            case 0:
                return false;
            default:
                System.out.println("Opção inválida!");
                pausar();
                break;
        }
        
        return true;
    }
    
    /**
     * Exibe menu principal quando há usuário logado
     * @return true para continuar, false para sair
     */
    private boolean exibirMenuPrincipal() {
        limparTela();
        Usuario usuario = usuarioController.getUsuarioLogado();
        System.out.println("=== MENU PRINCIPAL ===");
        System.out.println("Usuário: " + usuario.getNome() + " (" + usuario.getEmail() + ")\n");
        
        System.out.println("1. 📅 Cadastrar Evento");
        System.out.println("2. 👀 Visualizar Todos os Eventos");
        System.out.println("3. 🔍 Buscar Eventos");
        System.out.println("4. ✅ Participar de Evento");
        System.out.println("5. 📋 Meus Eventos");
        System.out.println("6. ❌ Cancelar Participação");
        System.out.println("7. 📊 Estatísticas");
        System.out.println("8. 👤 Meu Perfil");
        System.out.println("9. 🚪 Logout");
        System.out.println("0. ❌ Sair do Sistema");
        System.out.print("\nEscolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                cadastrarEvento();
                break;
            case 2:
                visualizarTodosEventos();
                break;
            case 3:
                buscarEventos();
                break;
            case 4:
                participarEvento();
                break;
            case 5:
                visualizarMeusEventos();
                break;
            case 6:
                cancelarParticipacao();
                break;
            case 7:
                exibirEstatisticas();
                break;
            case 8:
                gerenciarPerfil();
                break;
            case 9:
                usuarioController.fazerLogout();
                break;
            case 0:
                return false;
            default:
                System.out.println("Opção inválida!");
                pausar();
                break;
        }
        
        return true;
    }
    
    // Métodos de autenticação
    
    /**
     * Realiza login do usuário
     */
    private void realizarLogin() {
        limparTela();
        System.out.println("=== LOGIN ===\n");
        
        System.out.print("Digite seu email: ");
        String email = scanner.nextLine().trim();
        
        if (email.isEmpty()) {
            System.out.println("Email não pode estar vazio!");
            pausar();
            return;
        }
        
        if (usuarioController.fazerLogin(email)) {
            System.out.println("Login realizado com sucesso!");
            pausar();
        } else {
            System.out.println("Usuário não encontrado. Deseja se cadastrar? (s/n)");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s") || resposta.equals("sim")) {
                cadastrarNovoUsuario();
            }
        }
    }
    
    /**
     * Cadastra novo usuário no sistema
     */
    private void cadastrarNovoUsuario() {
        limparTela();
        System.out.println("=== CADASTRO DE USUÁRIO ===\n");
        
        Usuario novoUsuario = new Usuario();
        
        // Nome
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Nome não pode estar vazio!");
            pausar();
            return;
        }
        novoUsuario.setNome(nome);
        
        // Email
        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            
            if (email.isEmpty()) {
                System.out.println("❌ Email não pode estar vazio! Tente novamente.\n");
                continue; // Volta para o início do loop
            }
            
            if (!usuarioController.isEmailValido(email)) {
                System.out.println("❌ Email inválido! Use o formato: exemplo@dominio.com\n");
                email = ""; // Força o loop a continuar
            } else if (usuarioController.buscarUsuarioPorEmail(email).isPresent()) {
                System.out.println("❌ Este email já está cadastrado! Use outro email.\n");
                email = ""; // Força o loop a continuar
            } else {
                System.out.println("✅ Email válido!");
            }
        } while (email.isEmpty());
        novoUsuario.setEmail(email);
        
        // Telefone
        String telefone;
        do {
            System.out.print("Telefone (com DDD): ");
            telefone = scanner.nextLine().trim();
            if (!usuarioController.isTelefoneValido(telefone)) {
                System.out.println("❌ Telefone inválido! Use formato: (11) 99999-9999 ou 11999999999\n");
            } else {
                System.out.println("✅ Telefone válido!");
            }
        } while (!usuarioController.isTelefoneValido(telefone));
        novoUsuario.setTelefone(telefone);
        
        // Cidade
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine().trim();
        if (cidade.isEmpty()) {
            System.out.println("Cidade não pode estar vazia!");
            pausar();
            return;
        }
        novoUsuario.setCidade(cidade);
        
        // Idade
        int idade;
        do {
            System.out.print("Idade: ");
            try {
                idade = Integer.parseInt(scanner.nextLine().trim());
                if (!usuarioController.isIdadeValida(idade)) {
                    System.out.println("❌ Idade deve estar entre 13 e 120 anos! Tente novamente.\n");
                    idade = 0;
                } else {
                    System.out.println("✅ Idade válida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Idade inválida! Digite apenas números.\n");
                idade = 0;
            }
        } while (idade == 0);
        novoUsuario.setIdade(idade);
        
        // Cadastra o usuário
        if (usuarioController.cadastrarUsuario(novoUsuario)) {
            System.out.println("\nUsuário cadastrado com sucesso!");
            System.out.println("Deseja fazer login agora? (s/n)");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s") || resposta.equals("sim")) {
                usuarioController.fazerLogin(email);
            }
        }
        pausar();
    }
    
    // Métodos de eventos
    
    /**
     * Cadastra novo evento
     */
    private void cadastrarEvento() {
        limparTela();
        System.out.println("=== CADASTRAR EVENTO ===\n");
        
        Evento novoEvento = new Evento();
        
        // Nome do evento
        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Nome do evento não pode estar vazio!");
            pausar();
            return;
        }
        novoEvento.setNome(nome);
        
        // Endereço
        System.out.print("Endereço completo: ");
        String endereco = scanner.nextLine().trim();
        if (endereco.isEmpty()) {
            System.out.println("Endereço não pode estar vazio!");
            pausar();
            return;
        }
        novoEvento.setEndereco(endereco);
        
        // Categoria
        System.out.println("\nCategorias disponíveis:");
        Categoria[] categorias = Categoria.values();
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i].getDescricao());
        }
        
        Categoria categoria = null;
        do {
            System.out.print("Escolha uma categoria (1-" + categorias.length + "): ");
            try {
                int opcaoCategoria = Integer.parseInt(scanner.nextLine().trim());
                if (opcaoCategoria >= 1 && opcaoCategoria <= categorias.length) {
                    categoria = categorias[opcaoCategoria - 1];
                } else {
                    System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido!");
            }
        } while (categoria == null);
        novoEvento.setCategoria(categoria);
        
        // Data e hora
        LocalDateTime horario = null;
        do {
            System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
            String dataHora = scanner.nextLine().trim();
            try {
                horario = LocalDateTime.parse(dataHora, FORMATO_DATA);
                if (horario.isBefore(LocalDateTime.now())) {
                    System.out.println("A data/hora deve ser futura!");
                    horario = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido! Use: dd/MM/yyyy HH:mm (ex: 25/12/2024 20:00)");
            }
        } while (horario == null);
        novoEvento.setHorario(horario);
        
        // Descrição
        System.out.print("Descrição do evento: ");
        String descricao = scanner.nextLine().trim();
        if (descricao.isEmpty()) {
            System.out.println("Descrição não pode estar vazia!");
            pausar();
            return;
        }
        novoEvento.setDescricao(descricao);
        
        // Cadastra o evento
        if (eventoController.adicionarEvento(novoEvento)) {
            System.out.println("\nEvento cadastrado com sucesso!");
            System.out.println("ID do evento: " + novoEvento.getId());
        }
        pausar();
    }
    
    /**
     * Visualiza todos os eventos
     */
    private void visualizarTodosEventos() {
        limparTela();
        System.out.println("=== TODOS OS EVENTOS ===\n");
        
        List<Evento> eventos = eventoController.listarEventosOrdenados();
        
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado ainda.");
        } else {
            exibirListaEventos(eventos, true);
        }
        
        pausar();
    }
    
    /**
     * Permite visualizar eventos sem estar logado
     */
    private void visualizarEventosSemLogin() {
        limparTela();
        System.out.println("=== EVENTOS DISPONÍVEIS ===\n");
        
        List<Evento> eventosProximos = eventoController.listarEventosProximos();
        
        if (eventosProximos.isEmpty()) {
            System.out.println("Nenhum evento próximo encontrado.");
        } else {
            System.out.println("Eventos próximos:");
            exibirListaEventos(eventosProximos, false);
            System.out.println("\n💡 Faça login para participar dos eventos!");
        }
        
        pausar();
    }
    
    /**
     * Busca eventos por diferentes critérios
     */
    private void buscarEventos() {
        limparTela();
        System.out.println("=== BUSCAR EVENTOS ===\n");
        System.out.println("1. Buscar por nome");
        System.out.println("2. Buscar por categoria");
        System.out.println("3. Eventos próximos");
        System.out.println("4. Eventos passados");
        System.out.println("5. Eventos acontecendo agora");
        System.out.print("\nEscolha o tipo de busca: ");
        
        int opcao = lerOpcao();
        List<Evento> resultados = null;
        
        switch (opcao) {
            case 1:
                System.out.print("Digite o nome (ou parte do nome): ");
                String nome = scanner.nextLine().trim();
                resultados = eventoController.buscarEventosPorNome(nome);
                break;
            case 2:
                Categoria categoria = escolherCategoria();
                if (categoria != null) {
                    resultados = eventoController.listarEventosPorCategoria(categoria);
                }
                break;
            case 3:
                resultados = eventoController.listarEventosProximos();
                break;
            case 4:
                resultados = eventoController.listarEventosPassados();
                break;
            case 5:
                resultados = eventoController.listarEventosAtuais();
                break;
            default:
                System.out.println("Opção inválida!");
                pausar();
                return;
        }
        
        if (resultados != null) {
            limparTela();
            System.out.println("=== RESULTADOS DA BUSCA ===\n");
            if (resultados.isEmpty()) {
                System.out.println("Nenhum evento encontrado.");
            } else {
                exibirListaEventos(resultados, true);
            }
        }
        
        pausar();
    }
    
    /**
     * Permite ao usuário participar de um evento
     */
    private void participarEvento() {
        limparTela();
        System.out.println("=== PARTICIPAR DE EVENTO ===\n");
        
        List<Evento> eventosProximos = eventoController.listarEventosProximos();
        
        if (eventosProximos.isEmpty()) {
            System.out.println("Nenhum evento próximo disponível para participação.");
            pausar();
            return;
        }
        
        System.out.println("Eventos disponíveis para participação:");
        for (int i = 0; i < eventosProximos.size(); i++) {
            Evento evento = eventosProximos.get(i);
            System.out.printf("%d. %s - %s (%s)\n", 
                    i + 1, evento.getNome(), evento.getHorarioFormatado(), evento.getStatus());
        }
        
        System.out.print("\nEscolha um evento (número) ou 0 para voltar: ");
        int opcao = lerOpcao();
        
        if (opcao > 0 && opcao <= eventosProximos.size()) {
            Evento eventoEscolhido = eventosProximos.get(opcao - 1);
            
            // Exibe detalhes do evento
            limparTela();
            System.out.println(eventoEscolhido.toStringDetalhado());
            
            System.out.print("\nConfirma participação neste evento? (s/n): ");
            String confirmacao = scanner.nextLine().trim().toLowerCase();
            
            if (confirmacao.equals("s") || confirmacao.equals("sim")) {
                if (eventoController.adicionarParticipante(eventoEscolhido.getId(), 
                        usuarioController.getUsuarioLogado())) {
                    System.out.println("✅ Participação confirmada com sucesso!");
                } else {
                    System.out.println("❌ Erro ao confirmar participação.");
                }
            } else {
                System.out.println("Participação cancelada.");
            }
        } else if (opcao != 0) {
            System.out.println("Opção inválida!");
        }
        
        pausar();
    }
    
    /**
     * Visualiza eventos do usuário logado
     */
    private void visualizarMeusEventos() {
        limparTela();
        System.out.println("=== MEUS EVENTOS ===\n");
        
        Usuario usuario = usuarioController.getUsuarioLogado();
        List<Evento> meusEventos = eventoController.listarEventosDoUsuario(usuario);
        
        if (meusEventos.isEmpty()) {
            System.out.println("Você não está participando de nenhum evento.");
        } else {
            System.out.println("Eventos que você está participando:");
            exibirListaEventos(meusEventos, true);
        }
        
        pausar();
    }
    
    /**
     * Permite cancelar participação em eventos
     */
    private void cancelarParticipacao() {
        limparTela();
        System.out.println("=== CANCELAR PARTICIPAÇÃO ===\n");
        
        Usuario usuario = usuarioController.getUsuarioLogado();
        List<Evento> meusEventos = eventoController.listarEventosDoUsuario(usuario);
        
        if (meusEventos.isEmpty()) {
            System.out.println("Você não está participando de nenhum evento.");
            pausar();
            return;
        }
        
        System.out.println("Seus eventos:");
        for (int i = 0; i < meusEventos.size(); i++) {
            Evento evento = meusEventos.get(i);
            System.out.printf("%d. %s - %s (%s)\n", 
                    i + 1, evento.getNome(), evento.getHorarioFormatado(), evento.getStatus());
        }
        
        System.out.print("\nEscolha um evento para cancelar participação (número) ou 0 para voltar: ");
        int opcao = lerOpcao();
        
        if (opcao > 0 && opcao <= meusEventos.size()) {
            Evento eventoEscolhido = meusEventos.get(opcao - 1);
            
            System.out.print("Confirma cancelamento da participação em '" + 
                    eventoEscolhido.getNome() + "'? (s/n): ");
            String confirmacao = scanner.nextLine().trim().toLowerCase();
            
            if (confirmacao.equals("s") || confirmacao.equals("sim")) {
                if (eventoController.removerParticipante(eventoEscolhido.getId(), usuario)) {
                    System.out.println("✅ Participação cancelada com sucesso!");
                } else {
                    System.out.println("❌ Erro ao cancelar participação.");
                }
            } else {
                System.out.println("Cancelamento não realizado.");
            }
        } else if (opcao != 0) {
            System.out.println("Opção inválida!");
        }
        
        pausar();
    }
    
    // Métodos utilitários
    
    /**
     * Exibe lista de eventos formatada
     * @param eventos lista de eventos
     * @param mostrarDetalhes se deve mostrar detalhes completos
     */
    private void exibirListaEventos(List<Evento> eventos, boolean mostrarDetalhes) {
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            System.out.println("─".repeat(60));
            
            if (mostrarDetalhes) {
                System.out.println(evento.toStringDetalhado());
            } else {
                System.out.printf("📅 %s\n", evento.getNome());
                System.out.printf("📍 %s\n", evento.getEndereco());
                System.out.printf("🏷️  %s\n", evento.getCategoria().getDescricao());
                System.out.printf("⏰ %s\n", evento.getHorarioFormatado());
                System.out.printf("📊 Status: %s\n", evento.getStatus());
                System.out.printf("👥 Participantes: %d\n", evento.getNumeroParticipantes());
            }
            
            if (i < eventos.size() - 1) {
                System.out.println();
            }
        }
        System.out.println("─".repeat(60));
    }
    
    /**
     * Permite escolher uma categoria
     * @return categoria escolhida ou null
     */
    private Categoria escolherCategoria() {
        System.out.println("\nCategorias disponíveis:");
        Categoria[] categorias = Categoria.values();
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i].getDescricao());
        }
        
        System.out.print("Escolha uma categoria (1-" + categorias.length + "): ");
        try {
            int opcao = Integer.parseInt(scanner.nextLine().trim());
            if (opcao >= 1 && opcao <= categorias.length) {
                return categorias[opcao - 1];
            } else {
                System.out.println("Opção inválida!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Digite um número válido!");
        }
        return null;
    }
    
    /**
     * Exibe estatísticas do sistema
     */
    private void exibirEstatisticas() {
        limparTela();
        System.out.println("=== ESTATÍSTICAS DO SISTEMA ===\n");
        
        System.out.println(eventoController.obterEstatisticas());
        System.out.println();
        System.out.println(usuarioController.obterEstatisticas());
        
        pausar();
    }
    
    /**
     * Gerencia perfil do usuário
     */
    private void gerenciarPerfil() {
        limparTela();
        Usuario usuario = usuarioController.getUsuarioLogado();
        System.out.println("=== MEU PERFIL ===\n");
        System.out.println(usuario.toString());
        
        System.out.println("\n1. Editar perfil");
        System.out.println("2. Excluir conta");
        System.out.println("0. Voltar");
        System.out.print("\nEscolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                editarPerfil();
                break;
            case 2:
                excluirConta();
                break;
            case 0:
                /* Voltar */
                break;
            default:
                System.out.println("Opção inválida!");
                pausar();
                break;
        }
    }
    
    /**
     * Edita perfil do usuário
     */
    private void editarPerfil() {
        System.out.println("\n=== EDITAR PERFIL ===\n");
        System.out.println("(Deixe em branco para manter o valor atual)\n");
        
        Usuario usuario = usuarioController.getUsuarioLogado();
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setEmail(usuario.getEmail()); // Email não pode ser alterado
        
        // Nome
        System.out.print("Nome atual: " + usuario.getNome() + "\nNovo nome: ");
        String nome = scanner.nextLine().trim();
        usuarioAtualizado.setNome(nome.isEmpty() ? usuario.getNome() : nome);
        
        // Telefone
        String telefone;
        do {
            System.out.print("Telefone atual: " + usuario.getTelefone() + "\nNovo telefone: ");
            telefone = scanner.nextLine().trim();
            if (telefone.isEmpty()) {
                telefone = usuario.getTelefone();
                break;
            }
            if (!usuarioController.isTelefoneValido(telefone)) {
                System.out.println("Telefone inválido! Tente novamente ou deixe em branco.");
                telefone = "";
            }
        } while (telefone.isEmpty());
        usuarioAtualizado.setTelefone(telefone);
        
        // Cidade
        System.out.print("Cidade atual: " + usuario.getCidade() + "\nNova cidade: ");
        String cidade = scanner.nextLine().trim();
        usuarioAtualizado.setCidade(cidade.isEmpty() ? usuario.getCidade() : cidade);
        
        // Idade
        int idade = usuario.getIdade();
        System.out.print("Idade atual: " + idade + "\nNova idade: ");
        String idadeStr = scanner.nextLine().trim();
        if (!idadeStr.isEmpty()) {
            try {
                int novaIdade = Integer.parseInt(idadeStr);
                if (usuarioController.isIdadeValida(novaIdade)) {
                    idade = novaIdade;
                } else {
                    System.out.println("Idade inválida! Mantendo valor atual.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Idade inválida! Mantendo valor atual.");
            }
        }
        usuarioAtualizado.setIdade(idade);
        
        // Atualiza usuário
        if (usuarioController.atualizarUsuario(usuarioAtualizado)) {
            System.out.println("✅ Perfil atualizado com sucesso!");
        } else {
            System.out.println("❌ Erro ao atualizar perfil.");
        }
        
        pausar();
    }
    
    /**
     * Exclui conta do usuário
     */
    private void excluirConta() {
        System.out.println("\n=== EXCLUIR CONTA ===\n");
        System.out.println("⚠️  ATENÇÃO: Esta ação é irreversível!");
        System.out.println("Todos os seus dados serão removidos do sistema.");
        System.out.print("\nDigite 'CONFIRMAR' para excluir sua conta: ");
        
        String confirmacao = scanner.nextLine().trim();
        
        if (confirmacao.equals("CONFIRMAR")) {
            String email = usuarioController.getUsuarioLogado().getEmail();
            if (usuarioController.removerUsuario(email)) {
                System.out.println("✅ Conta excluída com sucesso!");
                System.out.println("Obrigado por ter usado nosso sistema.");
                pausar();
            } else {
                System.out.println("❌ Erro ao excluir conta.");
                pausar();
            }
        } else {
            System.out.println("Exclusão cancelada.");
            pausar();
        }
    }
    
    /**
     * Lê opção numérica do usuário
     * @return opção escolhida ou -1 se inválida
     */
    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Limpa a tela do console
     */
    private void limparTela() {
        try {
            // Para Windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Para Linux/Mac
                System.out.print("\033[2J\033[H");
            }
        } catch (Exception e) {
            // Se não conseguir limpar, apenas adiciona linhas
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    /**
     * Pausa execução aguardando Enter
     */
    private void pausar() {
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Finaliza o sistema salvando dados
     */
    private void finalizarSistema() {
        System.out.println("\n=== FINALIZANDO SISTEMA ===");
        
        // Salva dados antes de sair
        eventoController.salvarEventos();
        usuarioController.salvarUsuarios();
        
        System.out.println("Dados salvos com sucesso!");
        System.out.println("Obrigado por usar o Sistema de Eventos!");
        System.out.println("Até logo! 👋");
        
        scanner.close();
    }
}