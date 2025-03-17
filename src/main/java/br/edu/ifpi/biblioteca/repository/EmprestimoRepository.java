//Repositório: Interfaces que comunicam as entidades com o banco, herdando de JpaRepository.

package br.edu.ifpi.biblioteca.repository;

import br.edu.ifpi.biblioteca.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}
