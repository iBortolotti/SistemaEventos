package com.example.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa um evento no sistema.
 * Contém todos os atributos obrigatórios e métodos para gerenciamento de participantes.
 * Implementa Serializable para permitir persistência em arquivo.
 * 
 * @author Sistema de Eventos
 * @version 1.0
 */
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributos obrigatórios do evento
    private String nome;
    private String endereco;
    private Categoria categoria;
    private LocalDateTime horario;
    private String descricao;
    
    // Lista de participantes confirmados
    private List<Usuario> participantes;
    
    // ID único do evento (gerado automaticamente)
    private long id;
    private static long contadorId = 1;
    
    /**
     * Construtor padrão
     */
    public Evento() {
        this.participantes = new ArrayList<>();
        this.id = contadorId++;
    }
    
    /**
     * Construtor com parâmetros
     * @param nome Nome do evento
     * @param endereco Endereço onde o evento ocorrerá
     * @param categoria Categoria do evento
     * @param horario Data e horário do evento
     * @param descricao Descrição detalhada do evento
     */
    public Evento(String nome, String endereco, Categoria categoria, 
                  LocalDateTime horario, String descricao) {
        this();
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }
    
    // Getters e Setters
    
    /**
     * Obtém o ID único do evento
     * @return ID do evento
     */
    public long getId() {
        return id;
    }
    
    /**
     * Obtém o nome do evento
     * @return nome do evento
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Define o nome do evento
     * @param nome nome do evento
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Obtém o endereço do evento
     * @return endereço do evento
     */
    public String getEndereco() {
        return endereco;
    }
    
    /**
     * Define o endereço do evento
     * @param endereco endereço do evento
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    /**
     * Obtém a categoria do evento
     * @return categoria do evento
     */
    public Categoria getCategoria() {
        return categoria;
    }
    
    /**
     * Define a categoria do evento
     * @param categoria categoria do evento
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    /**
     * Obtém o horário do evento
     * @return horário do evento
     */
    public LocalDateTime getHorario() {
        return horario;
    }
    
    /**
     * Define o horário do evento
     * @param horario horário do evento
     */
    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }
    
    /**
     * Obtém a descrição do evento
     * @return descrição do evento
     */
    public String getDescricao() {
        return descricao;
    }
    
    /**
     * Define a descrição do evento
     * @param descricao descrição do evento
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * Obtém a lista de participantes
     * @return lista de participantes
     */
    public List<Usuario> getParticipantes() {
        return participantes;
    }
    
    /**
     * Define a lista de participantes
     * @param participantes lista de participantes
     */
    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes != null ? participantes : new ArrayList<>();
    }
    
    // Métodos para gerenciamento de participantes
    
    /**
     * Adiciona um participante ao evento
     * @param usuario usuário a ser adicionado
     * @return true se adicionado com sucesso, false se já participava
     */
    public boolean adicionarParticipante(Usuario usuario) {
        if (usuario != null && !participantes.contains(usuario)) {
            return participantes.add(usuario);
        }
        return false;
    }
    
    /**
     * Remove um participante do evento
     * @param usuario usuário a ser removido
     * @return true se removido com sucesso, false se não participava
     */
    public boolean removerParticipante(Usuario usuario) {
        return participantes.remove(usuario);
    }
    
    /**
     * Verifica se um usuário é participante do evento
     * @param usuario usuário a ser verificado
     * @return true se é participante, false caso contrário
     */
    public boolean isParticipante(Usuario usuario) {
        return participantes.contains(usuario);
    }
    
    /**
     * Obtém o número de participantes
     * @return número de participantes
     */
    public int getNumeroParticipantes() {
        return participantes.size();
    }
    
    // Métodos de status do evento
    
    /**
     * Verifica se o evento já ocorreu
     * @return true se já ocorreu, false caso contrário
     */
    public boolean jaOcorreu() {
        return horario.isBefore(LocalDateTime.now());
    }
    
    /**
     * Verifica se o evento está ocorrendo agora
     * (considera uma margem de 1 hora)
     * @return true se está ocorrendo, false caso contrário
     */
    public boolean estaOcorrendo() {
        LocalDateTime agora = LocalDateTime.now();
        return !agora.isBefore(horario) && agora.isBefore(horario.plusHours(1));
    }
    
    /**
     * Obtém o status do evento
     * @return string com o status do evento
     */
    public String getStatus() {
        if (estaOcorrendo()) {
            return "Acontecendo agora";
        } else if (jaOcorreu()) {
            return "Já aconteceu";
        } else {
            return "Próximo evento";
        }
    }
    
    /**
     * Obtém o horário formatado do evento
     * @return string com data e hora formatadas
     */
    public String getHorarioFormatado() {
        return horario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    /**
     * Valida se os dados do evento estão corretos
     * @return true se válido, false caso contrário
     */
    public boolean isValido() {
        return nome != null && !nome.trim().isEmpty() &&
               endereco != null && !endereco.trim().isEmpty() &&
               categoria != null &&
               horario != null &&
               descricao != null && !descricao.trim().isEmpty();
    }
    
    /**
     * Verifica se dois eventos são iguais baseado no ID
     * @param obj objeto a ser comparado
     * @return true se os eventos são iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Evento evento = (Evento) obj;
        return id == evento.id;
    }
    
    /**
     * Gera hash code baseado no ID
     * @return hash code do evento
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    /**
     * Representação textual do evento
     * @return string representando o evento
     */
    @Override
    public String toString() {
        return String.format("Evento{id=%d, nome='%s', endereco='%s', categoria=%s, " +
                           "horario=%s, participantes=%d, status='%s'}",
                id, nome, endereco, categoria, getHorarioFormatado(), 
                getNumeroParticipantes(), getStatus());
    }
    
    /**
     * Retorna uma representação detalhada do evento
     * @return string com informações detalhadas
     */
    public String toStringDetalhado() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== EVENTO ===\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Endereço: ").append(endereco).append("\n");
        sb.append("Categoria: ").append(categoria.getDescricao()).append("\n");
        sb.append("Data/Hora: ").append(getHorarioFormatado()).append("\n");
        sb.append("Descrição: ").append(descricao).append("\n");
        sb.append("Status: ").append(getStatus()).append("\n");
        sb.append("Participantes: ").append(getNumeroParticipantes());
        
        if (!participantes.isEmpty()) {
            sb.append("\n--- Lista de Participantes ---");
            for (int i = 0; i < participantes.size(); i++) {
                Usuario p = participantes.get(i);
                sb.append("\n").append(i + 1).append(". ").append(p.getNome())
                  .append(" (").append(p.getEmail()).append(")");
            }
        }
        
        return sb.toString();
    }
}