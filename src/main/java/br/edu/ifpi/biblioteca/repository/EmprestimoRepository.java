package br.edu.ifpi.biblioteca.repository;

import br.edu.ifpi.biblioteca.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> { 
    List<Emprestimo> findByUsuarioId(Long usuarioId); 
}