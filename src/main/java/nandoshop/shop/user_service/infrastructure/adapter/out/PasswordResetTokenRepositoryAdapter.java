package nandoshop.shop.user_service.infrastructure.adapter.out;

import nandoshop.shop.user_service.application.port.out.PasswordResetTokenRepositoryPort;
import nandoshop.shop.user_service.domain.model.PasswordResetToken;
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
        // 1. Verificar y eliminar token existente para este usuario
        PasswordResetTokenEntity existingToken = repository.findByUserId(user.getId());
        if (existingToken != null) {
            repository.delete(existingToken);
            repository.flush(); // Asegurar que el delete se ejecute antes del insert
        }

        // 2. Guardar el nuevo token
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

    @Override
    public PasswordResetToken findByToken(String token) {
        PasswordResetTokenEntity entity = repository.findByToken(token);
        if (entity == null) {
            return null;
        }
        return new PasswordResetToken(
                entity.getToken(),
                entity.getExpiresAt(),
                entity.getUser().toDomain(),
                entity.isUsed());
    }

    @Override
    public void update(PasswordResetToken token) {
        PasswordResetTokenEntity entity = repository.findByToken(token.getToken());
        if (entity != null) {
            entity.setUsed(token.isUsed());
            repository.save(entity);
        }
    }
}
