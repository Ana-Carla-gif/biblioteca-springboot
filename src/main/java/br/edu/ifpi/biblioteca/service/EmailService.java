package br.edu.ifpi.biblioteca.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import br.edu.ifpi.biblioteca.entity.Emprestimo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarNotificacaoEmprestimo(Emprestimo emprestimo) {
        try {
            // Validação dos dados obrigatórios
            if (emprestimo == null || emprestimo.getUsuario() == null || emprestimo.getLivro() == null) {
                throw new IllegalArgumentException("Dados do empréstimo incompletos");
            }

            String emailDestino = emprestimo.getUsuario().getEmail();
            if (emailDestino == null || emailDestino.trim().isEmpty()) {
                throw new IllegalArgumentException("E-mail do usuário não informado");
            }

            // Preparação da mensagem
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailDestino);
            message.setSubject("Confirmação de Empréstimo - Biblioteca IFPI");

            String nomeUsuario = emprestimo.getUsuario().getNome() != null ? 
                emprestimo.getUsuario().getNome() : "Cliente";
            String tituloLivro = emprestimo.getLivro().getTitulo() != null ? 
                emprestimo.getLivro().getTitulo() : "Livro sem título";
            String autorLivro = emprestimo.getLivro().getAutor() != null ? 
                emprestimo.getLivro().getAutor() : "Autor desconhecido";

            message.setText(String.format(
                "Olá %s,\n\nVocê realizou um empréstimo na nossa biblioteca:\n\n" +
                "Livro: %s\n" +
                "Autor: %s\n" +
                "Data do empréstimo: %s\n" +
                "Data de devolução: %s\n\n" +
                "Agradecemos por utilizar nossos serviços!",
                nomeUsuario,
                tituloLivro,
                autorLivro,
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao()
            ));

            // Envio do e-mail
            mailSender.send(message);
            logger.info("E-mail de notificação enviado para: {}", emailDestino);
            
        } catch (Exception e) {
            logger.error("Falha ao enviar e-mail de notificação", e);
            throw new RuntimeException("Falha ao enviar notificação por e-mail: " + e.getMessage());
        }
    }
}

