package nandoshop.shop.user_service.infrastructure.adapter.out.email;

import jakarta.mail.internet.MimeMessage;
import nandoshop.shop.user_service.application.port.out.EmailSenderPort;
import nandoshop.shop.user_service.domain.model.Email;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpEmailSenderAdapter implements EmailSenderPort {
    private final JavaMailSender mailSender;

    public SmtpEmailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(Email email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);
            mailSender.send(message);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al enviar email: " + e.getMessage(), e);
        }
    }

}
