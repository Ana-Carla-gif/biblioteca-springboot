package br.edu.ifpi.biblioteca.controllers;

import br.edu.ifpi.biblioteca.Dto.EmprestimoDto;
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

    @PostMapping
    public Emprestimo realizarEmprestimo(@RequestBody EmprestimoDto emprestimoDto) {
        // Busca o livro pelo ID
        Livro livro = livroRepository.findById(emprestimoDto.getLivroId()).orElseThrow();

        // Busca o usuário pelo ID
        Usuario usuario = usuarioRepository.findById(emprestimoDto.getUsuarioId()).orElseThrow();

        // Verifica se o livro está disponível
        if (!livro.isDisponivel()) {
            throw new RuntimeException("Livro não está disponível para empréstimo");
        }

        // Cria o empréstimo
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(emprestimoDto.getDataEmprestimo());
        emprestimo.setDataDevolucao(emprestimoDto.getDataDevolucao());

        // Atualiza o status do livro para indisponível
        livro.setDisponivel(false);
        livroRepository.save(livro);

        // Salva o empréstimo no banco de dados
        return emprestimoRepository.save(emprestimo);
    }

    @GetMapping
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }
}