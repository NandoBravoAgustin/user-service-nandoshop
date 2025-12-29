package nandoshop.shop.user_service.application.port.in.useCase;

public interface SendSupportEmailUseCase {
    void sendPasswordResetEmail(String to);

    void verifyPasswordResetToken(String token);

    void resetPassword(String token, String newPassword);
}
