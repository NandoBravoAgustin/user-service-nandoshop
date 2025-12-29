package nandoshop.shop.user_service.domain.model;

import nandoshop.shop.user_service.domain.exception.InvalidTokenException;

import java.time.Instant;

public class PasswordResetToken {
    private final String token;
    private final Instant expiresAt;
    private final User user;
    private final boolean used;

    public PasswordResetToken(String token, Instant expiresAt, User user, boolean used) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.user = user;
        this.used = used;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public User getUser() {
        return user;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public void validate() {
        if (isExpired()) {
            throw new InvalidTokenException("El token ha expirado");
        }
        if (isUsed()) {
            throw new InvalidTokenException("El token ya ha sido utilizado");
        }
    }

    public PasswordResetToken markAsUsed() {
        return new PasswordResetToken(this.token, this.expiresAt, this.user, true);
    }
}
