package br.edu.ifpi.biblioteca.controllers;

import br.edu.ifpi.biblioteca.Dto.UsuarioDto;
import br.edu.ifpi.biblioteca.entity.Usuario;
import br.edu.ifpi.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public Usuario cadastrarUsuario(@RequestBody UsuarioDto usuarioDto) {
        try {
            // Cria um novo usuário com os dados do DTO
            Usuario usuario = new Usuario();
            usuario.setNome(usuarioDto.getNome());
            usuario.setCpf(usuarioDto.getCpf());
            usuario.setEmail(usuarioDto.getEmail());
    
            // Salva o usuário no banco de dados
            return usuarioRepository.save(usuario);
        } catch (Exception ex) {
            // Log do erro (para depuração)
            System.err.println("Erro ao cadastrar usuário: " + ex.getMessage());
            ex.printStackTrace(); // Isso imprime o stack trace no console
    
            // Lança a exceção novamente para que o Spring retorne um erro 500
            throw new RuntimeException("Erro ao cadastrar usuário: " + ex.getMessage(), ex);
        }
    }
}