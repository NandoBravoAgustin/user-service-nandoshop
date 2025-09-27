package nandoshop.shop.user_service.infrastructure.repository;

import nandoshop.shop.user_service.infrastructure.adapter.out.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
}
