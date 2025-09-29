package nandoshop.shop.user_service.application.port.in.useCase;

import nandoshop.shop.user_service.application.port.in.command.RegisterUserCommand;
import nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.UserResponse;

public interface RegisterUserUseCase {
    UserResponse register(RegisterUserCommand command);
}
