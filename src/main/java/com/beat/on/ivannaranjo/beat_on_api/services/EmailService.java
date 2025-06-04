package com.beat.on.ivannaranjo.beat_on_api.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private final String fromEmail;

    public EmailService(JavaMailSender emailSender,
                        TemplateEngine templateEngine,
                        @Value("${spring.mail.username}") String fromEmail) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.fromEmail = fromEmail;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        validateEmailParameters(to, subject);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendHtmlMessage(String to, String subject, String text) {
        validateEmailParameters(to, subject);

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariable("subject", subject);
            context.setVariable("text", text);

            String htmlContent = templateEngine.process("email-template", context);
            helper.setText(htmlContent, true);

            emailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailException("Fallo al enviar el email a " + to, e);
        }
    }

    private void validateEmailParameters(String to, String subject) {
        if (to == null || to.isBlank()) {
            throw new IllegalArgumentException("El contenido del email no puede ser nulo o vacío");
        }
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("El asunto no puede ser nulo o vacío");
        }
    }

    public static class EmailException extends RuntimeException {
        public EmailException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}