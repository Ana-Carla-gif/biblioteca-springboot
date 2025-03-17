package br.edu.ifpi.biblioteca.controllers;

import br.edu.ifpi.biblioteca.Dto.LivroDto;
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

    @PostMapping
    public Livro cadastrarLivro(@RequestBody LivroDto livroDto) {
        Livro livro = new Livro();
        livro.setTitulo(livroDto.getTitulo());
        livro.setAno(livroDto.getAno());
        livro.setEditora(livroDto.getEditora());
        livro.setAutor(livroDto.getAutor());
        livro.setDisponivel(livroDto.isDisponivel());
        return livroRepository.save(livro);
    }

    @GetMapping
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }
}