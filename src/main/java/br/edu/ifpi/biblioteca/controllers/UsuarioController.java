package br.edu.ifpi.biblioteca.controllers;

import br.edu.ifpi.biblioteca.Dto.UsuarioDto;
import br.edu.ifpi.biblioteca.entity.Usuario;
import br.edu.ifpi.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public Usuario cadastrarUsuario(@RequestBody UsuarioDto usuarioDto) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(usuarioDto.getNome());
            usuario.setCpf(usuarioDto.getCpf());
            usuario.setEmail(usuarioDto.getEmail());

            return usuarioRepository.save(usuario);
        } catch (Exception ex) {
            System.err.println("Erro ao cadastrar usuário: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar usuário: " + ex.getMessage(), ex);
        }
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
    }
}
