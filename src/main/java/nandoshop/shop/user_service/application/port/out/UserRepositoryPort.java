package nandoshop.shop.user_service.application.port.out;

import nandoshop.shop.user_service.domain.model.User;

public interface UserRepositoryPort {
    boolean existsByEmail(String email);
    User save(User user);
}