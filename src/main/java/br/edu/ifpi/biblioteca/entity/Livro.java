package br.edu.ifpi.biblioteca.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotNull
    @Column(nullable = false)
    private int ano;

    @NotBlank
    @Column(nullable = false)
    private String editora;

    @NotBlank
    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private boolean disponivel = true;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    
    public static Livro criarLivro(String titulo, int ano, String editora, String autor, boolean disponivel) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAno(ano);
        livro.setEditora(editora);
        livro.setAutor(autor);
        livro.setDisponivel(disponivel);
        return livro;
    }
}