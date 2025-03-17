package br.edu.ifpi.biblioteca.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioDto(
    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 123.456.789-09")
    String cpf,

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    String email
) {}

// package br.edu.ifpi.biblioteca.Dto;
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;

// public record UsuarioDto (@NotBlank  String nome, String cpf, @NotBlank @Email String email){

// }
    

