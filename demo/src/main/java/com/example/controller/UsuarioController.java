package com.example.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.model.Usuario;

/**
 * Controller responsável por gerenciar os usuários do sistema.
 * Implementa funcionalidades de CRUD e validação de usuários.
 * 
 * @author Sistema de Eventos
 * @version 1.0
 */
public class UsuarioController {
    
    // Lista de usuários em memória
    private final List<Usuario> usuarios;
    
    // Arquivo de persistência dos usuários
    private static final String ARQUIVO_USUARIOS = "usuarios.data";
    private final File arquivo;
    
    // Usuário logado no sistema
    private Usuario usuarioLogado;
    
    /**
     * Construtor que inicializa o controller e carrega usuários do arquivo
     */
    public UsuarioController() {
        this.arquivo = new File(ARQUIVO_USUARIOS);
        this.usuarios = carregarUsuarios();
        this.usuarioLogado = null;
    }
    
    // Métodos de persistência
    
    /**
     * Carrega usuários do arquivo de dados
     * @return lista de usuários carregados ou lista vazia se arquivo não existir
     */
    @SuppressWarnings("unchecked")
    private List<Usuario> carregarUsuarios() {
        List<Usuario> usuariosCarregados = new ArrayList<>();
        
        if (arquivo.exists() && arquivo.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                Object objeto = ois.readObject();
                if (objeto instanceof List<?>) {
                    usuariosCarregados = (List<Usuario>) objeto;
                    System.out.println("Usuários carregados com sucesso: " + usuariosCarregados.size() + " usuários encontrados.");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar usuários do arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Arquivo de usuários não encontrado. Iniciando com lista vazia.");
        }
        
        return usuariosCarregados;
    }
    
    /**
     * Salva usuários no arquivo de dados
     * @return true se salvou com sucesso, false caso contrário
     */
    public boolean salvarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(usuarios);
            System.out.println("Usuários salvos com sucesso no arquivo " + ARQUIVO_USUARIOS);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
            return false;
        }
    }
    
    // Métodos CRUD
    
    /**
     * Cadastra um novo usuário no sistema
     * @param usuario usuário a ser cadastrado
     * @return true se cadastrado com sucesso, false caso contrário
     */
    public boolean cadastrarUsuario(Usuario usuario) {
        if (usuario == null || !usuario.isValido()) {
            System.err.println("Dados do usuário inválidos.");
            return false;
        }
        
        // Verifica se já existe usuário com o mesmo email
        if (buscarUsuarioPorEmail(usuario.getEmail()).isPresent()) {
            System.err.println("Já existe um usuário cadastrado com este email.");
            return false;
        }
        
        boolean adicionado = usuarios.add(usuario);
        if (adicionado) {
            salvarUsuarios();
            System.out.println("Usuário '" + usuario.getNome() + "' cadastrado com sucesso!");
        }
        return adicionado;
    }
    
    /**
     * Atualiza dados de um usuário existente
     * @param usuarioAtualizado usuário com dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizarUsuario(Usuario usuarioAtualizado) {
        if (usuarioAtualizado == null || !usuarioAtualizado.isValido()) {
            return false;
        }
        
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equals(usuarioAtualizado.getEmail())) {
                usuarios.set(i, usuarioAtualizado);
                salvarUsuarios();
                System.out.println("Usuário atualizado com sucesso!");
                
                // Atualiza usuário logado se for o mesmo
                if (usuarioLogado != null && usuarioLogado.getEmail().equals(usuarioAtualizado.getEmail())) {
                    usuarioLogado = usuarioAtualizado;
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * Remove um usuário do sistema
     * @param email email do usuário a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    public boolean removerUsuario(String email) {
        Optional<Usuario> usuarioOpt = buscarUsuarioPorEmail(email);
        if (usuarioOpt.isPresent()) {
            boolean removido = usuarios.remove(usuarioOpt.get());
            if (removido) {
                salvarUsuarios();
                System.out.println("Usuário removido com sucesso!");
                
                // Faz logout se for o usuário logado
                if (usuarioLogado != null && usuarioLogado.getEmail().equals(email)) {
                    usuarioLogado = null;
                }
            }
            return removido;
        }
        System.err.println("Usuário não encontrado.");
        return false;
    }
    
    // Métodos de busca
    
    /**
     * Busca usuário por email
     * @param email email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return usuarios.stream()
                .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();
    }
    
    /**
     * Busca usuários por nome (busca parcial, case-insensitive)
     * @param nome nome ou parte do nome do usuário
     * @return lista de usuários encontrados
     */
    public List<Usuario> buscarUsuariosPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String nomeBusca = nome.toLowerCase().trim();
        return usuarios.stream()
                .filter(usuario -> usuario.getNome().toLowerCase().contains(nomeBusca))
                .sorted(Comparator.comparing(Usuario::getNome))
                .toList();
    }
    
    /**
     * Busca usuários por cidade
     * @param cidade cidade dos usuários
     * @return lista de usuários da cidade especificada
     */
    public List<Usuario> buscarUsuariosPorCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return usuarios.stream()
                .filter(usuario -> usuario.getCidade().equalsIgnoreCase(cidade.trim()))
                .sorted(Comparator.comparing(Usuario::getNome))
                .toList();
    }
    
    /**
     * Lista todos os usuários cadastrados
     * @return lista de todos os usuários
     */
    public List<Usuario> listarTodosUsuarios() {
        return usuarios.stream()
                .sorted(Comparator.comparing(Usuario::getNome))
                .toList();
    }
    
    // Métodos de autenticação/login
    
    /**
     * Realiza login do usuário no sistema
     * @param email email do usuário
     * @return true se login realizado com sucesso, false caso contrário
     */
    public boolean fazerLogin(String email) {
        Optional<Usuario> usuarioOpt = buscarUsuarioPorEmail(email);
        if (usuarioOpt.isPresent()) {
            usuarioLogado = usuarioOpt.get();
            System.out.println("Login realizado com sucesso! Bem-vindo(a), " + usuarioLogado.getNome() + "!");
            return true;
        }
        System.err.println("Usuário não encontrado. Verifique o email informado.");
        return false;
    }
    
    /**
     * Realiza logout do usuário
     */
    public void fazerLogout() {
        if (usuarioLogado != null) {
            System.out.println("Logout realizado. Até logo, " + usuarioLogado.getNome() + "!");
            usuarioLogado = null;
        }
    }
    
    /**
     * Verifica se existe um usuário logado
     * @return true se há usuário logado, false caso contrário
     */
    public boolean temUsuarioLogado() {
        return usuarioLogado != null;
    }
    
    /**
     * Obtém o usuário atualmente logado
     * @return usuário logado ou null se ninguém estiver logado
     */
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    // Métodos de validação
    
    /**
     * Valida formato de email
     * @param email email a ser validado
     * @return true se email é válido, false caso contrário
     */
    public boolean isEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Validação simples de email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Valida formato de telefone
     * @param telefone telefone a ser validado
     * @return true se telefone é válido, false caso contrário
     */
    public boolean isTelefoneValido(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return false;
        }
        
        // Remove caracteres não numéricos
        String telefoneNumeros = telefone.replaceAll("\\D", "");
        
        // Verifica se tem entre 10 e 11 dígitos (telefone brasileiro)
        return telefoneNumeros.length() >= 10 && telefoneNumeros.length() <= 11;
    }
    
    /**
     * Valida idade do usuário
     * @param idade idade a ser validada
     * @return true se idade é válida, false caso contrário
     */
    public boolean isIdadeValida(int idade) {
        return idade >= 13 && idade <= 120; // Sistema permite usuários de 13 a 120 anos
    }
    
    // Métodos utilitários
    
    /**
     * Obtém o total de usuários cadastrados
     * @return número total de usuários
     */
    public int getTotalUsuarios() {
        return usuarios.size();
    }
    
    /**
     * Verifica se existem usuários cadastrados
     * @return true se existem usuários, false caso contrário
     */
    public boolean temUsuarios() {
        return !usuarios.isEmpty();
    }
    
    /**
     * Obtém estatísticas dos usuários
     * @return string com estatísticas
     */
    public String obterEstatisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTATÍSTICAS DOS USUÁRIOS ===\n");
        stats.append("Total de usuários: ").append(getTotalUsuarios()).append("\n");
        
        if (!usuarios.isEmpty()) {
            // Estatísticas por cidade
            Map<String, Long> usuariosPorCidade = usuarios.stream()
                    .collect(Collectors.groupingBy(Usuario::getCidade, Collectors.counting()));
            
            stats.append("\n--- Por Cidade ---\n");
            usuariosPorCidade.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> stats.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"));
            
            // Estatísticas por faixa etária
            long jovens = usuarios.stream().filter(u -> u.getIdade() <= 25).count();
            long adultos = usuarios.stream().filter(u -> u.getIdade() > 25 && u.getIdade() <= 60).count();
            long idosos = usuarios.stream().filter(u -> u.getIdade() > 60).count();
            
            stats.append("\n--- Por Faixa Etária ---\n");
            stats.append("Jovens (até 25 anos): ").append(jovens).append("\n");
            stats.append("Adultos (26-60 anos): ").append(adultos).append("\n");
            stats.append("Idosos (60+ anos): ").append(idosos).append("\n");
        }
        
        return stats.toString();
    }
    
    /**
     * Limpa todos os usuários (use com cuidado!)
     * @return true se limpou com sucesso
     */
    public boolean limparTodosUsuarios() {
        usuarios.clear();
        usuarioLogado = null;
        return salvarUsuarios();
    }
}