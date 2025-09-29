package nandoshop.shop.user_service.application.port.in.command;

public record RegisterUserCommand(String email, String rawPassword, String name) {
}
