package nandoshop.shop.user_service.domain.model;

public class EmailVerification {
    private Long id;
    private Long userId;
    private String verificationCode;

    private boolean used;

    public EmailVerification(Long id, Long userId, String verificationCode, boolean used) {
        this.id = id;
        this.userId = userId;
        this.verificationCode = verificationCode;
        this.used = used;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public boolean isUsed() {
        return used;
    }
}
