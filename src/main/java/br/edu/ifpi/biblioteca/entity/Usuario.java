package br.edu.ifpi.biblioteca.entity;

import br.edu.ifpi.biblioteca.Dto.UsuarioDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String cpf;
    private String email;
   
    public Usuario() {}

    public Usuario(Long id, String nome, String cpf, String email, String preferenciaNotificacao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        
    }

    public Usuario(UsuarioDto usuarioDto) {
        this.nome = usuarioDto.nome();
        this.cpf = usuarioDto.cpf(); // Adicione esta linha para atribuir o CPF
        this.email = usuarioDto.email();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   
}