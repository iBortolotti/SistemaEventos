package com.example.model;

/**
 * Enum que representa as categorias disponíveis para eventos.
 * Define os tipos de eventos que podem ser cadastrados no sistema.
 * 
 * @author Sistema de Eventos
 * @version 1.0
 */
public enum Categoria {
    /**
     * Categoria para festas em geral (aniversários, formaturas, etc.)
     */
    FESTA("Festa"),
    
    /**
     * Categoria para eventos esportivos (jogos, campeonatos, corridas, etc.)
     */
    ESPORTE("Esporte"),
    
    /**
     * Categoria para shows musicais e apresentações artísticas
     */
    SHOW("Show"),
    
    /**
     * Categoria para palestras, seminários e eventos educacionais
     */
    PALESTRA("Palestra"),
    
    /**
     * Categoria para outros tipos de eventos não especificados
     */
    OUTROS("Outros");
    
    private final String descricao;
    
    /**
     * Construtor do enum
     * @param descricao descrição legível da categoria
     */
    Categoria(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * Obtém a descrição da categoria
     * @return descrição da categoria
     */
    public String getDescricao() {
        return descricao;
    }
    
    /**
     * Retorna a descrição da categoria
     * @return descrição da categoria
     */
    @Override
    public String toString() {
        return descricao;
    }
    
    /**
     * Busca uma categoria pelo nome (case-insensitive)
     * @param nome nome da categoria a ser buscada
     * @return categoria encontrada ou null se não encontrada
     */
    public static Categoria buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }
        
        for (Categoria categoria : values()) {
            if (categoria.getDescricao().equalsIgnoreCase(nome.trim()) ||
                categoria.name().equalsIgnoreCase(nome.trim())) {
                return categoria;
            }
        }
        return null;
    }
    
    /**
     * Lista todas as categorias disponíveis
     * @return array com todas as categorias
     */
    public static String[] listarCategorias() {
        Categoria[] categorias = values();
        String[] nomes = new String[categorias.length];
        for (int i = 0; i < categorias.length; i++) {
            nomes[i] = categorias[i].getDescricao();
        }
        return nomes;
    }
}