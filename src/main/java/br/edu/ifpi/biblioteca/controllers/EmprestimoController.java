package br.edu.ifpi.biblioteca.controllers;

import br.edu.ifpi.biblioteca.entity.Emprestimo;
import br.edu.ifpi.biblioteca.entity.Livro;
import br.edu.ifpi.biblioteca.entity.Usuario;
import br.edu.ifpi.biblioteca.repository.EmprestimoRepository;
import br.edu.ifpi.biblioteca.repository.LivroRepository;
import br.edu.ifpi.biblioteca.repository.UsuarioRepository;
import br.edu.ifpi.biblioteca.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private static final Logger logger = LoggerFactory.getLogger(EmprestimoController.class);
    
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarEmprestimos() {
        try {
            List<Emprestimo> emprestimos = emprestimoRepository.findAll();
            return ResponseEntity.ok(emprestimos);
        } catch (Exception e) {
            logger.error("Erro ao listar empréstimos", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarEmprestimoPorId(@PathVariable Long id) {
        return emprestimoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> realizarEmprestimo(@RequestBody Emprestimo emprestimo) {
        try {
            
            if (emprestimo.getLivro() == null || emprestimo.getLivro().getId() == null) {
                return ResponseEntity.badRequest().body("ID do livro não informado");
            }
            if (emprestimo.getUsuario() == null || emprestimo.getUsuario().getId() == null) {
                return ResponseEntity.badRequest().body("ID do usuário não informado");
            }

            // Busca as entidades relacionadas
            Livro livro = livroRepository.findById(emprestimo.getLivro().getId())
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + emprestimo.getLivro().getId()));
            
            Usuario usuario = usuarioRepository.findById(emprestimo.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + emprestimo.getUsuario().getId()));

            
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Usuário não possui e-mail cadastrado");
            }

            if (!livro.isDisponivel()) {
                return ResponseEntity.badRequest().body("Livro não está disponível para empréstimo");
            }

            livro.setDisponivel(false);
            livroRepository.save(livro);

            emprestimo.setLivro(livro);
            emprestimo.setUsuario(usuario);

            Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);
            
            try {
                emailService.enviarNotificacaoEmprestimo(emprestimoSalvo);
            } catch (Exception e) {
                logger.error("Erro ao enviar e-mail, mas o empréstimo foi registrado", e);
                // Continua mesmo com falha no e-mail
            }

            return ResponseEntity.ok(emprestimoSalvo);

        } catch (Exception e) {
            logger.error("Erro ao processar empréstimo", e);
            return ResponseEntity.internalServerError()
                    .body("Erro ao processar empréstimo: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEmprestimo(@PathVariable Long id, @RequestBody Emprestimo emprestimoAtualizado) {
        try {
            return emprestimoRepository.findById(id)
                    .map(emprestimo -> {
                        emprestimo.setDataEmprestimo(emprestimoAtualizado.getDataEmprestimo());
                        emprestimo.setDataDevolucao(emprestimoAtualizado.getDataDevolucao());
                        
                        if (emprestimoAtualizado.getLivro() != null && 
                            !emprestimo.getLivro().getId().equals(emprestimoAtualizado.getLivro().getId())) {
                            Livro novoLivro = livroRepository.findById(emprestimoAtualizado.getLivro().getId())
                                    .orElseThrow(() -> new RuntimeException("Novo livro não encontrado"));
                            emprestimo.setLivro(novoLivro);
                        }
                        
                        if (emprestimoAtualizado.getUsuario() != null && 
                            !emprestimo.getUsuario().getId().equals(emprestimoAtualizado.getUsuario().getId())) {
                            Usuario novoUsuario = usuarioRepository.findById(emprestimoAtualizado.getUsuario().getId())
                                    .orElseThrow(() -> new RuntimeException("Novo usuário não encontrado"));
                            emprestimo.setUsuario(novoUsuario);
                        }
                        
                        return ResponseEntity.ok(emprestimoRepository.save(emprestimo));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Erro ao atualizar empréstimo", e);
            return ResponseEntity.internalServerError()
                    .body("Erro ao atualizar empréstimo: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerEmprestimo(@PathVariable Long id) {
        try {
            return emprestimoRepository.findById(id)
                    .map(emprestimo -> {
                        // Libera o livro associado
                        Livro livro = emprestimo.getLivro();
                        livro.setDisponivel(true);
                        livroRepository.save(livro);
                        
                        emprestimoRepository.delete(emprestimo);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Erro ao remover empréstimo", e);
            return ResponseEntity.internalServerError()
                    .body("Erro ao remover empréstimo: " + e.getMessage());
        }
    }
}

//COLOQUEI DIVERSOS TRY CATCH PQ ESTAVA COM MUITOS ERROS KKK