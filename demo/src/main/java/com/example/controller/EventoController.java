package com.example.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.model.Categoria;
import com.example.model.Evento;
import com.example.model.Usuario;

/**
 * Controller responsável por gerenciar os eventos do sistema.
 * Implementa funcionalidades de CRUD, ordenação, filtragem e persistência.
 * 
 * @author Sistema de Eventos
 * @version 1.0
 */
public class EventoController {
    
    // Lista de eventos em memória
    private final List<Evento> eventos;
    
    // Arquivo de persistência
    private static final String ARQUIVO_EVENTOS = "events.data";
    private final File arquivo;
    
    /**
     * Construtor que inicializa o controller e carrega eventos do arquivo
     */
    public EventoController() {
        this.arquivo = new File(ARQUIVO_EVENTOS);
        this.eventos = carregarEventos();
    }
    
    // Métodos de persistência
    
    /**
     * Carrega eventos do arquivo de dados
     * @return lista de eventos carregados ou lista vazia se arquivo não existir
     */
    @SuppressWarnings("unchecked")
    private List<Evento> carregarEventos() {
        List<Evento> eventosCarregados = new ArrayList<>();
        
        if (arquivo.exists() && arquivo.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                Object objeto = ois.readObject();
                if (objeto instanceof List<?>) {
                    eventosCarregados = (List<Evento>) objeto;
                    System.out.println("Eventos carregados com sucesso: " + eventosCarregados.size() + " eventos encontrados.");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar eventos do arquivo: " + e.getMessage());
                // Se houver erro na leitura, cria arquivo de backup
                criarBackupArquivo();
            }
        } else {
            System.out.println("Arquivo de eventos não encontrado. Iniciando com lista vazia.");
        }
        
        return eventosCarregados;
    }
    
    /**
     * Salva eventos no arquivo de dados
     * @return true se salvou com sucesso, false caso contrário
     */
    public boolean salvarEventos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(eventos);
            System.out.println("Eventos salvos com sucesso no arquivo " + ARQUIVO_EVENTOS);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar eventos: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cria backup do arquivo em caso de erro
     */
    private void criarBackupArquivo() {
        String nomeBackup = ARQUIVO_EVENTOS + ".backup." + System.currentTimeMillis();
        try {
            if (arquivo.exists()) {
                Files.copy(arquivo.toPath(), new File(nomeBackup).toPath());
                System.out.println("Backup criado: " + nomeBackup);
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar backup: " + e.getMessage());
        }
    }
    
    // Métodos CRUD
    
    /**
     * Adiciona um novo evento ao sistema
     * @param evento evento a ser adicionado
     * @return true se adicionado com sucesso, false caso contrário
     */
    public boolean adicionarEvento(Evento evento) {
        if (evento == null || !evento.isValido()) {
            System.err.println("Evento inválido. Não foi possível adicionar.");
            return false;
        }
        
        boolean adicionado = eventos.add(evento);
        if (adicionado) {
            salvarEventos();
            System.out.println("Evento '" + evento.getNome() + "' adicionado com sucesso!");
        }
        return adicionado;
    }
    
    /**
     * Remove um evento do sistema
     * @param eventoId ID do evento a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    public boolean removerEvento(long eventoId) {
        Optional<Evento> eventoOpt = buscarEventoPorId(eventoId);
        if (eventoOpt.isPresent()) {
            boolean removido = eventos.remove(eventoOpt.get());
            if (removido) {
                salvarEventos();
                System.out.println("Evento removido com sucesso!");
            }
            return removido;
        }
        System.err.println("Evento com ID " + eventoId + " não encontrado.");
        return false;
    }
    
    /**
     * Atualiza um evento existente
     * @param eventoAtualizado evento com dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizarEvento(Evento eventoAtualizado) {
        if (eventoAtualizado == null || !eventoAtualizado.isValido()) {
            return false;
        }
        
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getId() == eventoAtualizado.getId()) {
                eventos.set(i, eventoAtualizado);
                salvarEventos();
                System.out.println("Evento atualizado com sucesso!");
                return true;
            }
        }
        return false;
    }
    
    // Métodos de busca e listagem
    
    /**
     * Busca evento por ID
     * @param id ID do evento
     * @return Optional contendo o evento se encontrado
     */
    public Optional<Evento> buscarEventoPorId(long id) {
        return eventos.stream()
                .filter(evento -> evento.getId() == id)
                .findFirst();
    }
    
    /**
     * Lista todos os eventos ordenados por data
     * @return lista de eventos ordenada
     */
    public List<Evento> listarEventosOrdenados() {
        return eventos.stream()
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }
    
    /**
     * Lista eventos por categoria
     * @param categoria categoria dos eventos
     * @return lista de eventos da categoria especificada
     */
    public List<Evento> listarEventosPorCategoria(Categoria categoria) {
        return eventos.stream()
                .filter(evento -> evento.getCategoria() == categoria)
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }
    
    /**
     * Lista eventos próximos (futuros)
     * @return lista de eventos que ainda vão acontecer
     */
    public List<Evento> listarEventosProximos() {
        LocalDateTime agora = LocalDateTime.now();
        return eventos.stream()
                .filter(evento -> evento.getHorario().isAfter(agora))
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }
    
    /**
     * Lista eventos que já aconteceram
     * @return lista de eventos passados
     */
    public List<Evento> listarEventosPassados() {
        LocalDateTime agora = LocalDateTime.now();
        return eventos.stream()
                .filter(evento -> evento.getHorario().isBefore(agora))
                .sorted(Comparator.comparing(Evento::getHorario).reversed())
                .collect(Collectors.toList());
    }
    
    /**
     * Lista eventos que estão acontecendo agora
     * @return lista de eventos atuais
     */
    public List<Evento> listarEventosAtuais() {
        return eventos.stream()
                .filter(Evento::estaOcorrendo)
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca eventos por nome (busca parcial, case-insensitive)
     * @param nome nome ou parte do nome do evento
     * @return lista de eventos encontrados
     */
    public List<Evento> buscarEventosPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String nomeBusca = nome.toLowerCase().trim();
        return eventos.stream()
                .filter(evento -> evento.getNome().toLowerCase().contains(nomeBusca))
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }
    
    // Métodos de participação
    
    /**
     * Adiciona participante a um evento
     * @param eventoId ID do evento
     * @param usuario usuário a ser adicionado
     * @return true se adicionado com sucesso, false caso contrário
     */
    public boolean adicionarParticipante(long eventoId, Usuario usuario) {
        Optional<Evento> eventoOpt = buscarEventoPorId(eventoId);
        if (eventoOpt.isPresent() && usuario != null) {
            Evento evento = eventoOpt.get();
            
            // Verifica se o evento já passou
            if (evento.jaOcorreu()) {
                System.err.println("Não é possível participar de um evento que já aconteceu.");
                return false;
            }
            
            boolean adicionado = evento.adicionarParticipante(usuario);
            if (adicionado) {
                salvarEventos();
                System.out.println("Participação confirmada no evento: " + evento.getNome());
            } else {
                System.out.println("Usuário já está participando deste evento.");
            }
            return adicionado;
        }
        return false;
    }
    
    /**
     * Remove participante de um evento
     * @param eventoId ID do evento
     * @param usuario usuário a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    public boolean removerParticipante(long eventoId, Usuario usuario) {
        Optional<Evento> eventoOpt = buscarEventoPorId(eventoId);
        if (eventoOpt.isPresent() && usuario != null) {
            Evento evento = eventoOpt.get();
            boolean removido = evento.removerParticipante(usuario);
            if (removido) {
                salvarEventos();
                System.out.println("Participação cancelada no evento: " + evento.getNome());
            } else {
                System.out.println("Usuário não estava participando deste evento.");
            }
            return removido;
        }
        return false;
    }
    
    /**
     * Lista eventos onde o usuário está participando
     * @param usuario usuário a ser verificado
     * @return lista de eventos onde o usuário participa
     */
    public List<Evento> listarEventosDoUsuario(Usuario usuario) {
        if (usuario == null) {
            return new ArrayList<>();
        }
        
        return eventos.stream()
                .filter(evento -> evento.isParticipante(usuario))
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }
    
    // Métodos utilitários
    
    /**
     * Obtém o total de eventos cadastrados
     * @return número total de eventos
     */
    public int getTotalEventos() {
        return eventos.size();
    }
    
    /**
     * Verifica se existem eventos cadastrados
     * @return true se existem eventos, false caso contrário
     */
    public boolean temEventos() {
        return !eventos.isEmpty();
    }
    
    /**
     * Limpa todos os eventos (use com cuidado!)
     * @return true se limpou com sucesso
     */
    public boolean limparTodosEventos() {
        eventos.clear();
        return salvarEventos();
    }
    
    /**
     * Obtém estatísticas dos eventos
     * @return string com estatísticas
     */
    public String obterEstatisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTATÍSTICAS DOS EVENTOS ===\n");
        stats.append("Total de eventos: ").append(getTotalEventos()).append("\n");
        stats.append("Eventos próximos: ").append(listarEventosProximos().size()).append("\n");
        stats.append("Eventos passados: ").append(listarEventosPassados().size()).append("\n");
        stats.append("Eventos acontecendo agora: ").append(listarEventosAtuais().size()).append("\n");
        
        // Estatísticas por categoria
        Map<Categoria, Long> eventosPorCategoria = eventos.stream()
                .collect(Collectors.groupingBy(Evento::getCategoria, Collectors.counting()));
        
        stats.append("\n--- Por Categoria ---\n");
        for (Map.Entry<Categoria, Long> entry : eventosPorCategoria.entrySet()) {
            stats.append(entry.getKey().getDescricao()).append(": ").append(entry.getValue()).append("\n");
        }
        
        return stats.toString();
    }
}