package nandoshop.shop.user_service.domain.service;

import nandoshop.shop.user_service.application.port.out.EmailSenderPort;
import nandoshop.shop.user_service.domain.model.Email;
import nandoshop.shop.user_service.infrastructure.adapter.out.email.EmailTemplateProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService {

    private final EmailSenderPort emailSenderPort;

    private final EmailTemplateProcessor templateProcessor;

    public EmailService(EmailSenderPort emailSenderPort, EmailTemplateProcessor templateProcessor) {
        this.emailSenderPort = emailSenderPort;
        this.templateProcessor = templateProcessor;
    }

    public void sendWelcomeEmail(String to, String username) {
        String subject = "¡Bienvenido " + username + "!";
        String body = templateProcessor.renderTemplate(
                "emails/welcome.html",
                Map.of("username", username, "title", "Bienvenido", "headerTitle", "NandoShop")
        );
        emailSenderPort.sendEmail(new Email(to, subject, body));
    }

    public void sendPasswordResetEmail(String to, String username, String resetLink) {
        String subject = "Restablecimiento de contraseña";
        String body = templateProcessor.renderTemplate(
                "emails/password_reset.html",
                Map.of("username", username, "resetLink", resetLink, "title", "Restablecimiento de Contraseña", "headerTitle", "NandoShop")
        );
        emailSenderPort.sendEmail(new Email(to, subject, body));
    }

    public void sendVerifyEmail(String to, String username, String verifyLink) {
        String subject = "Verificación de correo electrónico";
        String body = templateProcessor.renderTemplate(
                "emails/verify_email.html",
                Map.of("username", username, "verifyLink", verifyLink, "title", "Verificación de Correo Electrónico", "headerTitle", "NandoShop")
        );
        emailSenderPort.sendEmail(new Email(to, subject, body));
    }
}
