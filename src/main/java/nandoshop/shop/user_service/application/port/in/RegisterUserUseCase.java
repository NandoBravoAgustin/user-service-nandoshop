package nandoshop.shop.user_service.application.port.in;

import nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.UserResponse;

public interface RegisterUserUseCase {
    UserResponse register(RegisterUserCommand command);
}
