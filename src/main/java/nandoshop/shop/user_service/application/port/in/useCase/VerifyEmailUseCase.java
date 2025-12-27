package nandoshop.shop.user_service.application.port.in.useCase;

public interface VerifyEmailUseCase {
    void verify(String token);
}
