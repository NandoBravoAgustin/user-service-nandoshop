package nandoshop.shop.user_service.application.port.out;

import java.time.Instant;
import nandoshop.shop.user_service.domain.model.User;

public interface PasswordResetTokenRepositoryPort {
    void save(User user, String token, Instant expiresAt);
}
