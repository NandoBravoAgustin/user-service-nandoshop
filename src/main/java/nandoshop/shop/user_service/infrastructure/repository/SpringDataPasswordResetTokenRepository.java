package nandoshop.shop.user_service.infrastructure.repository;

import nandoshop.shop.user_service.infrastructure.adapter.out.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
}
