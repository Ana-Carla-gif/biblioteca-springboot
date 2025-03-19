package br.edu.ifpi.biblioteca.controllers;

import br.edu.ifpi.biblioteca.entity.Emprestimo;
import br.edu.ifpi.biblioteca.entity.Livro;
import br.edu.ifpi.biblioteca.entity.Usuario;
import br.edu.ifpi.biblioteca.repository.EmprestimoRepository;
import br.edu.ifpi.biblioteca.repository.LivroRepository;
import br.edu.ifpi.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    
    @GetMapping
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

 
    @GetMapping("/{id}")
    public Emprestimo buscarEmprestimoPorId(@PathVariable Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado com o ID: " + id));
    }


    @PostMapping
    public Emprestimo realizarEmprestimo(@RequestBody Emprestimo emprestimo) {
        Livro livro = livroRepository.findById(emprestimo.getLivro().getId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + emprestimo.getLivro().getId()));
        Usuario usuario = usuarioRepository.findById(emprestimo.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + emprestimo.getUsuario().getId()));

        if (!livro.isDisponivel()) {
            throw new RuntimeException("Livro não está disponível para empréstimo");
        }

        livro.setDisponivel(false);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

   
    @PutMapping("/{id}")
    public Emprestimo atualizarEmprestimo(@PathVariable Long id, @RequestBody Emprestimo emprestimoAtualizado) {
        return emprestimoRepository.findById(id)
                .map(emprestimo -> {
                    emprestimo.setDataEmprestimo(emprestimoAtualizado.getDataEmprestimo());
                    emprestimo.setDataDevolucao(emprestimoAtualizado.getDataDevolucao());
                    emprestimo.setLivro(emprestimoAtualizado.getLivro());
                    emprestimo.setUsuario(emprestimoAtualizado.getUsuario());
                    return emprestimoRepository.save(emprestimo);
                })
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado com o ID: " + id));
    }

    @DeleteMapping("/{id}")
    public void removerEmprestimo(@PathVariable Long id) {
        if (!emprestimoRepository.existsById(id)) {
            throw new RuntimeException("Empréstimo não encontrado com o ID: " + id);
        }
        emprestimoRepository.deleteById(id);
    }
}