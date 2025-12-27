package nandoshop.shop.user_service.infrastructure.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import nandoshop.shop.user_service.application.port.in.command.RegisterUserCommand;
import nandoshop.shop.user_service.application.port.in.useCase.RegisterUserUseCase;
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
    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Este endpoint permite registrar un usuario en el sistema enviando email, password y nombre."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario registrado con éxito",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida (validaciones no cumplidas)",
                    content = @Content
            )
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(
                request.email(),
                request.password(),
                request.name()
        );
        return ResponseEntity.ok(registerUserUseCase.register(command));
    }
    @PostMapping("/send")
    public ResponseEntity<Void> verifyEmail(@RequestBody String token) {
        // Lógica para verificar el email usando el token
        return ResponseEntity.ok().build();
    }
}
