package br.edu.ifpi.biblioteca.controllers;

import br.edu.ifpi.biblioteca.entity.Livro;
import br.edu.ifpi.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

   
    @GetMapping
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    @GetMapping("/{id}")
    public Livro buscarLivroPorId(@PathVariable Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + id));
    }
    @PostMapping
    public Livro cadastrarLivro(@RequestBody Livro livro) {
        return livroRepository.save(livro);
    }

    @PutMapping("/{id}")
    public Livro atualizarLivro(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livro.setTitulo(livroAtualizado.getTitulo());
                    livro.setAno(livroAtualizado.getAno());
                    livro.setEditora(livroAtualizado.getEditora());
                    livro.setAutor(livroAtualizado.getAutor());
                    livro.setDisponivel(livroAtualizado.isDisponivel());
                    return livroRepository.save(livro);
                })
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + id));
    }

   
    @DeleteMapping("/{id}")
    public void removerLivro(@PathVariable Long id) {
        if (!livroRepository.existsById(id)) {
            throw new RuntimeException("Livro não encontrado com o ID: " + id);
        }
        livroRepository.deleteById(id);
    }
}