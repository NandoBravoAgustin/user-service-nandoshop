package nandoshop.shop.user_service.infrastructure.adapter.in;

import jakarta.validation.Valid;
import nandoshop.shop.user_service.application.port.in.RegisterUserCommand;
import nandoshop.shop.user_service.application.port.in.RegisterUserUseCase;
import nandoshop.shop.user_service.infrastructure.adapter.in.dto.request.RegisterUserRequest;
import nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(
                request.email(),
                request.password(),
                request.name()
        );
        return ResponseEntity.ok(registerUserUseCase.register(command));
    }

}
