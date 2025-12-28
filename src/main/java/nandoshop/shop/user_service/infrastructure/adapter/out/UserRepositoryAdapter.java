package nandoshop.shop.user_service.infrastructure.adapter.out;

import nandoshop.shop.user_service.application.port.out.UserRepositoryPort;
import nandoshop.shop.user_service.domain.model.User;
import nandoshop.shop.user_service.infrastructure.repository.SpringDataUserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final SpringDataUserRepository repository;

    public UserRepositoryAdapter(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public User findByEmail(String email) {
        UserEntity entity = repository.findByEmail(email);
        if (entity == null) {
            return null;
        }
        return entity.toDomain();
    }
}
