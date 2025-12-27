package nandoshop.shop.user_service.infrastructure.adapter.out;

import jakarta.persistence.*;
import nandoshop.shop.user_service.domain.model.User;

import java.time.LocalDateTime;

@Entity
public class EmailVerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private UserEntity userId;
    private String verificationCode;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean used;
}
