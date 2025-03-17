package br.edu.ifpi.biblioteca.repository;

import br.edu.ifpi.biblioteca.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByDisponivel(boolean disponivel);
}