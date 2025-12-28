package nandoshop.shop.user_service.infrastructure.adapter.out;

import nandoshop.shop.user_service.application.port.out.PasswordResetTokenRepositoryPort;
import nandoshop.shop.user_service.domain.model.User;
import nandoshop.shop.user_service.infrastructure.repository.SpringDataPasswordResetTokenRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class PasswordResetTokenRepositoryAdapter implements PasswordResetTokenRepositoryPort {

    private final SpringDataPasswordResetTokenRepository repository;

    public PasswordResetTokenRepositoryAdapter(SpringDataPasswordResetTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user, String token, Instant expiresAt) {
        PasswordResetTokenEntity entity = new PasswordResetTokenEntity();
        entity.setToken(token);
        entity.setExpiresAt(expiresAt);
        entity.setUsed(false);

        // Creamos una referencia al usuario solo con el ID para la FK
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        entity.setUser(userEntity);

        repository.save(entity);
    }
}
