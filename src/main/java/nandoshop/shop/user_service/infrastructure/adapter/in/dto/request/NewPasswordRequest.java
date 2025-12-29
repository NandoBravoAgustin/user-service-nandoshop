package nandoshop.shop.user_service.infrastructure.adapter.in.dto.request;

public record NewPasswordRequest(String token, String newPassword) {
}
