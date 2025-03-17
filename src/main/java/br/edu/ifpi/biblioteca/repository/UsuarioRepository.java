package br.edu.ifpi.biblioteca.repository;

import br.edu.ifpi.biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
 
    
