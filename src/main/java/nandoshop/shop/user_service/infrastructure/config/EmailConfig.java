package nandoshop.shop.user_service.infrastructure.config;

import nandoshop.shop.user_service.application.port.out.EmailSenderPort;
import nandoshop.shop.user_service.domain.service.EmailService;
import nandoshop.shop.user_service.infrastructure.adapter.out.email.EmailTemplateProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    @Bean
    public EmailService emailService(EmailSenderPort emailSenderPort, EmailTemplateProcessor templateProcessor) {
        return new EmailService(emailSenderPort, templateProcessor);
    }
}
