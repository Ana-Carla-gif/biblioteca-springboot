package br.edu.ifpi.biblioteca.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity  // ISSO INDICA QUE ESSA CLASSE É UMA TABELA NO BANCO DE DADOS

@Table (name = "emprestimos")
public class Emprestimo {
    @Id // INDICA QUE O ID É UMA CHAVE PRIMÁRIA

    @GeneratedValue(strategy = GenerationType.IDENTITY) // ESSA ANOTAÇÃO FAZ COM QUE O ID SEJA GERADO AUTOMATICAMENTE
    private long id;

    @ManyToOne  //COMO SE FOSSE UMA CHAVE ESTRANGEIRA
    private Usuario usuario;

    @ManyToOne
    private Livro livro;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public Emprestimo() {}

    public Emprestimo(Usuario usuario, Livro livro, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
