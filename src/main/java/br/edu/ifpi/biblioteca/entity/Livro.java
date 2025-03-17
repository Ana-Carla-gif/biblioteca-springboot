package br.edu.ifpi.biblioteca.entity;
//PRECISA MODIFICAR PARA USAR DTO
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

@Table (name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private int ano;
    private String editora;
    private String autor;
    private boolean disponivel;

    public Livro() {}

    public Livro(long id, String titulo, int ano, String editora, String autor, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.editora = editora;
        this.autor = autor;
        this.disponivel = disponivel;
    }


public Long getId() {
    return id;
}

public void setId(long id) {
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
}
