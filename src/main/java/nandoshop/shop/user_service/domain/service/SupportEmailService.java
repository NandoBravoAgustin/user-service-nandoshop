package nandoshop.shop.user_service.domain.service;

import org.springframework.stereotype.Service;

import nandoshop.shop.user_service.application.port.in.useCase.SendSupportEmailUseCase;
import nandoshop.shop.user_service.application.port.out.PasswordResetTokenRepositoryPort;
import nandoshop.shop.user_service.application.port.out.UserRepositoryPort;
import nandoshop.shop.user_service.domain.model.User;

@Service
public class SupportEmailService implements SendSupportEmailUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordResetTokenRepositoryPort tokenRepositoryPort;
    private final EmailService emailService;

    public SupportEmailService(UserRepositoryPort userRepositoryPort,
            PasswordResetTokenRepositoryPort tokenRepositoryPort,
            EmailService emailService) {
        this.userRepositoryPort = userRepositoryPort;
        this.tokenRepositoryPort = tokenRepositoryPort;
        this.emailService = emailService;
    }

    @Override
    public void sendPasswordResetEmail(String to) {
        User user = userRepositoryPort.findByEmail(to);
        if (user == null) {
            return;
        }

        String token = java.util.UUID.randomUUID().toString();
        // Expiración en 15 minutos (900 segundos)
        java.time.Instant expiresAt = java.time.Instant.now().plusSeconds(900);

        tokenRepositoryPort.save(user, token, expiresAt);

        // Link de ejemplo (debería venir de properties o ser dinámico)
        String link = "http://localhost:3000/reset-password?token=" + token;

        emailService.sendPasswordResetEmail(to, user.getName(), link);
    }
}
