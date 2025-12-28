package nandoshop.shop.user_service.infrastructure.adapter.out;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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
