package nandoshop.shop.user_service.domain.service;

import org.springframework.stereotype.Service;

import nandoshop.shop.user_service.application.port.in.useCase.SendSupportEmailUseCase;
import nandoshop.shop.user_service.application.port.out.PasswordResetTokenRepositoryPort;
import nandoshop.shop.user_service.application.port.out.UserRepositoryPort;
import nandoshop.shop.user_service.domain.model.User;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import nandoshop.shop.user_service.domain.model.PasswordResetToken;
import nandoshop.shop.user_service.domain.exception.InvalidTokenException;

@Service
public class SupportEmailService implements SendSupportEmailUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordResetTokenRepositoryPort tokenRepositoryPort;
    private final EmailService emailService;

    private final Argon2PasswordEncoder passwordEncoder;

    public SupportEmailService(UserRepositoryPort userRepositoryPort,
            PasswordResetTokenRepositoryPort tokenRepositoryPort,
            EmailService emailService,
            Argon2PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.tokenRepositoryPort = tokenRepositoryPort;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public void verifyPasswordResetToken(String token) {
        PasswordResetToken resetToken = tokenRepositoryPort.findByToken(token);
        if (resetToken == null) {
            throw new InvalidTokenException("Token no encontrado");
        }
        resetToken.validate();
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepositoryPort.findByToken(token);
        if (resetToken == null) {
            throw new InvalidTokenException("Token no encontrado");
        }
        resetToken.validate();

        User user = resetToken.getUser();
        user.changePassword(passwordEncoder.encode(newPassword));
        userRepositoryPort.save(user);

        tokenRepositoryPort.update(resetToken.markAsUsed());
    }
}
