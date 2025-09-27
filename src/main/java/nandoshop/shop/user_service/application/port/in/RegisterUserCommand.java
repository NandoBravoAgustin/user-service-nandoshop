package nandoshop.shop.user_service.application.port.in;

public record RegisterUserCommand(String email, String rawPassword, String name) {
}
