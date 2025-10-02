package com.example;

import com.example.view.MenuPrincipal;

/**
 * Classe principal do Sistema de Eventos.
 * 
 * Este sistema permite cadastrar e gerenciar eventos em uma cidade,
 * incluindo funcionalidades de:
 * - Cadastro de usuários
 * - Cadastro de eventos
 * - Participação em eventos
 * - Visualização de eventos ordenados por data
 * - Persistência de dados em arquivos
 * 
 * O sistema foi desenvolvido seguindo o padrão MVC (Model-View-Controller)
 * e utiliza conceitos de orientação a objetos como:
 * - Encapsulamento
 * - Herança (implicitamente através de interfaces como Serializable)
 * - Polimorfismo (através de interfaces e métodos sobrescritos)
 * - Abstração (através de separação de responsabilidades)
 * 
 * Estrutura do projeto:
 * - model: Classes que representam os dados (Usuario, Evento, Categoria)
 * - controller: Classes que gerenciam a lógica de negócio (EventoController, UsuarioController)
 * - view: Classes que gerenciam a interface com o usuário (MenuPrincipal)
 * 
 * Funcionalidades implementadas:
 * ✅ Cadastro de usuários com validação
 * ✅ Sistema de login/logout
 * ✅ Cadastro de eventos com categorias predefinidas
 * ✅ Listagem de eventos ordenados por data/hora
 * ✅ Participação e cancelamento de participação em eventos
 * ✅ Filtros de eventos (próximos, passados, atuais, por categoria)
 * ✅ Persistência em arquivos (events.data e usuarios.data)
 * ✅ Interface console amigável com menus organizados
 * ✅ Validações de dados de entrada
 * ✅ Tratamento de erros
 * ✅ Estatísticas do sistema
 * ✅ Gerenciamento de perfil do usuário
 * 
 * @author Sistema de Eventos - PSC USJT
 * @version 1.0
 * @since 2024
 */
public class App {
    
    /**
     * Método principal que inicia o sistema.
     * 
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        try {
            // Exibe informações sobre o sistema
            exibirInformacoesSistema();
            
            // Cria e inicia o menu principal
            MenuPrincipal menu = new MenuPrincipal();
            menu.iniciar();
            
        } catch (Exception e) {
            // Tratamento de erro geral para evitar que o sistema trave
            System.err.println("Erro crítico no sistema: " + e.getMessage());
            System.err.println("O sistema será encerrado.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Exibe informações sobre o sistema e tecnologias utilizadas.
     */
    private static void exibirInformacoesSistema() {
        System.out.println("Iniciando Sistema de Eventos...");
        System.out.println("Versão: 1.0");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("OS: " + System.getProperty("os.name"));
        System.out.println("Arquitetura: " + System.getProperty("os.arch"));
        System.out.println();
        
        // Aguarda um momento para o usuário ver as informações
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
