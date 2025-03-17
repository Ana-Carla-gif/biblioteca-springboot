package br.edu.ifpi.biblioteca.repository;

import br.edu.ifpi.biblioteca.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
