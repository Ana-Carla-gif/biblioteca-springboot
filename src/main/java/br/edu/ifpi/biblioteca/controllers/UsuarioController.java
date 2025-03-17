package br.edu.ifpi.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ifpi.biblioteca.Dto.UsuarioDto;
import br.edu.ifpi.biblioteca.entity.Usuario;
import br.edu.ifpi.biblioteca.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todos os usuários
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Adicionar um novo usuário
    @PostMapping
    public ResponseEntity<String> adicionarUsuario(@RequestBody UsuarioDto dados) {
        Usuario usuario = new Usuario(dados);
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
    }

    // Atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Usuário atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }

    // Deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }
}




// package br.edu.ifpi.biblioteca.controllers;

// import java.util.List;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import br.edu.ifpi.biblioteca.Dto.UsuarioDto;
// import br.edu.ifpi.biblioteca.entity.Usuario;
// import br.edu.ifpi.biblioteca.repository.UsuarioRepository;
// import jakarta.validation.Valid;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// //Define que esta classe é um controlador REST;
// @RestController
// //Não precisei fazer o import de dto.usuario.Dto.
// @RequestMapping("/usuarios") //DEFINE URL 
// public class UsuarioController {
//     @Autowired
//     private UsuarioRepository usuarioRepository;

//     @GetMapping() //Metodo que retorna a lista de todos os usuários cadastrados
//     public List<Usuario> listaTodosUsuarios() {
//         return usuarioRepository.findAll();
//     }
   
    
//     @PostMapping //Metodo para adicionar um novo usuário ao banco de dados
//     //vamos ultilizar os dados da minha record Dto
//     //@Valid ele é uma notação de valida
//     public ResponseEntity<String> addUsuario(@RequestBody @Valid UsuarioDto dados) {
//         //criar alguma coisa aqui;
//         Usuario usuario = new Usuario (dados);
//         usuarioRepository.save(usuario);
//         return ResponseEntity.ok().build();
//     }
// //Metodo para atualizar os dados de um usuario ao banco de dados
//     @PutMapping("/{id}") 
//     public ResponseEntity<String> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
//         if (usuarioRepository.existsById(id)) {
//             usuario.setId(id);
//             usuarioRepository.save(usuario);
//             return ResponseEntity.ok().build();
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }
// //Metodo para excluir um usuario do banco de dados
//     @DeleteMapping("/{id}")
//     public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
//         if (usuarioRepository.existsById(id)) {
//             usuarioRepository.deleteById(id);
//             return ResponseEntity.ok().build();
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }
// }