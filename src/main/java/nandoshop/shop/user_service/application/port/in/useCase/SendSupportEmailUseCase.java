package nandoshop.shop.user_service.application.port.in.useCase;

public interface SendSupportEmailUseCase {
    void sendPasswordResetEmail(String to);
}
