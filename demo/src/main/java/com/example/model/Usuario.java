package com.example.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que representa um usuário do sistema de eventos.
 * Implementa Serializable para permitir persistência em arquivo.
 * 
 * @author Sistema de Eventos
 * @version 1.0
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributos obrigatórios do usuário
    private String nome;
    private String email;
    private String telefone;
    private String cidade;
    private int idade;
    
    /**
     * Construtor padrão
     */
    public Usuario() {}
    
    /**
     * Construtor com parâmetros
     * @param nome Nome completo do usuário
     * @param email E-mail do usuário
     * @param telefone Telefone do usuário
     * @param cidade Cidade onde o usuário reside
     * @param idade Idade do usuário
     */
    public Usuario(String nome, String email, String telefone, String cidade, int idade) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cidade = cidade;
        this.idade = idade;
    }
    
    // Getters e Setters
    
    /**
     * Obtém o nome do usuário
     * @return nome do usuário
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Define o nome do usuário
     * @param nome nome do usuário
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Obtém o email do usuário
     * @return email do usuário
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Define o email do usuário
     * @param email email do usuário
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Obtém o telefone do usuário
     * @return telefone do usuário
     */
    public String getTelefone() {
        return telefone;
    }
    
    /**
     * Define o telefone do usuário
     * @param telefone telefone do usuário
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    /**
     * Obtém a cidade do usuário
     * @return cidade do usuário
     */
    public String getCidade() {
        return cidade;
    }
    
    /**
     * Define a cidade do usuário
     * @param cidade cidade do usuário
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    /**
     * Obtém a idade do usuário
     * @return idade do usuário
     */
    public int getIdade() {
        return idade;
    }
    
    /**
     * Define a idade do usuário
     * @param idade idade do usuário
     */
    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    /**
     * Verifica se dois usuários são iguais baseado no email
     * @param obj objeto a ser comparado
     * @return true se os usuários são iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return Objects.equals(email, usuario.email);
    }
    
    /**
     * Gera hash code baseado no email
     * @return hash code do usuário
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
    
    /**
     * Representação textual do usuário
     * @return string representando o usuário
     */
    @Override
    public String toString() {
        return String.format("Usuario{nome='%s', email='%s', telefone='%s', cidade='%s', idade=%d}",
                nome, email, telefone, cidade, idade);
    }
    
    /**
     * Valida se os dados do usuário estão corretos
     * @return true se válido, false caso contrário
     */
    public boolean isValido() {
        return nome != null && !nome.trim().isEmpty() &&
               email != null && !email.trim().isEmpty() && email.contains("@") &&
               telefone != null && !telefone.trim().isEmpty() &&
               cidade != null && !cidade.trim().isEmpty() &&
               idade > 0 && idade < 150;
    }
}