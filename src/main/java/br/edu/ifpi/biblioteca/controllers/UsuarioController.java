package br.edu.ifpi.biblioteca.controllers;

import br.edu.ifpi.biblioteca.entity.Usuario;
import br.edu.ifpi.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    
    @PostMapping
    public Usuario cadastrarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    
    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setCpf(usuarioAtualizado.getCpf());
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    
    @DeleteMapping("/{id}")
    public void removerUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}