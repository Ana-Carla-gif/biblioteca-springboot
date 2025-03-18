package br.edu.ifpi.biblioteca.controllers;

import br.edu.ifpi.biblioteca.Dto.EmprestimoDto;
import br.edu.ifpi.biblioteca.entity.Emprestimo;
import br.edu.ifpi.biblioteca.entity.Livro;
import br.edu.ifpi.biblioteca.entity.Usuario;
import br.edu.ifpi.biblioteca.repository.EmprestimoRepository;
import br.edu.ifpi.biblioteca.repository.LivroRepository;
import br.edu.ifpi.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public Emprestimo realizarEmprestimo(@RequestBody EmprestimoDto emprestimoDto) {
        Livro livro = livroRepository.findById((long) emprestimoDto.getLivroId()).orElseThrow();
        Usuario usuario = usuarioRepository.findById((long) emprestimoDto.getUsuarioId()).orElseThrow();

        if (!livro.isDisponivel()) {
            throw new RuntimeException("Livro não está disponível para empréstimo");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(emprestimoDto.getDataEmprestimo());
        emprestimo.setDataDevolucao(emprestimoDto.getDataDevolucao());

        livro.setDisponivel(false);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    @GetMapping
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarEmprestimoPorId(@PathVariable Long id) {
        return emprestimoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEmprestimo(@PathVariable Long id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);
        if (emprestimo.isPresent()) {
            emprestimoRepository.deleteById(id);
            return ResponseEntity.ok("Empréstimo deletado com sucesso!");
        } else {
            return ResponseEntity.status(404).body("Empréstimo não encontrado.");
        }
    }
}
