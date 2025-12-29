package nandoshop.shop.user_service.infrastructure.adapter.in;

import nandoshop.shop.user_service.application.port.in.useCase.SendSupportEmailUseCase;
import nandoshop.shop.user_service.domain.service.EmailService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import nandoshop.shop.user_service.infrastructure.adapter.in.dto.request.PasswordResetRequest;
import nandoshop.shop.user_service.infrastructure.adapter.in.dto.request.NewPasswordRequest;

@RestController
@RequestMapping("/api/v1/support")
@Tag(name = "Support", description = "Endpoints para soporte, emails y recuperación de contraseñas")
public class SupportEmailController {
    private final EmailService emailService;
    private final SendSupportEmailUseCase supportEmailService;

    public SupportEmailController(EmailService emailService, SendSupportEmailUseCase supportEmailService) {
        this.emailService = emailService;
        this.supportEmailService = supportEmailService;
    }

    @Operation(summary = "Enviar email de bienvenida", description = "Envía un email de bienvenida al usuario especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email enviado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno al enviar el email", content = @Content)
    })
    @PostMapping("/welcome")
    public ResponseEntity<nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.ApiResponse<Void>> sendWelcomeEmail(
            @RequestParam String to, @RequestParam String username) {
        emailService.sendWelcomeEmail(to, username);
        return ResponseEntity.ok(nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.ApiResponse
                .success("Email enviado correctamente a " + to));
    }

    @Operation(summary = "Solicitar restablecimiento de contraseña", description = "Envía un email con un link para restablecer la contraseña si el correo existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud procesada correctamente (siempre devuelve 200 por seguridad)"),
            @ApiResponse(responseCode = "400", description = "Email inválido", content = @Content)
    })
    @PostMapping("/send-password-reset")
    public ResponseEntity<nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.ApiResponse<Void>> sendPasswordResetEmail(
            @RequestBody PasswordResetRequest request) {
        supportEmailService.sendPasswordResetEmail(request.email());
        return ResponseEntity.ok(nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.ApiResponse
                .success("Si el correo electrónico existe, recibirás instrucciones para restablecer tu contraseña."));
    }

    @Operation(summary = "Verificar token de restablecimiento", description = "Verifica si un token de restablecimiento es válido y no ha expirado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token válido"),
            @ApiResponse(responseCode = "400", description = "Token inválido, expirado o ya usado", content = @Content)
    })
    @PostMapping("/verify-password-reset-token")
    public ResponseEntity<nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.ApiResponse<Void>> verifyPasswordResetToken(
            @RequestParam String token) {
        supportEmailService.verifyPasswordResetToken(token);
        return ResponseEntity
                .ok(nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.ApiResponse
                        .success("Token de restablecimiento de contraseña verificado correctamente"));
    }

    @Operation(summary = "Restablecer contraseña", description = "Cambia la contraseña del usuario utilizando un token válido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña cambiada correctamente"),
            @ApiResponse(responseCode = "400", description = "Token inválido, expirado o contraseña no cumple requisitos", content = @Content)
    })
    @PostMapping("/reset-password")
    public ResponseEntity<nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.ApiResponse<Void>> resetPassword(
            @RequestBody NewPasswordRequest request) {
        supportEmailService.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok(nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.ApiResponse
                .success("Contraseña restablecida correctamente"));
    }

    @Operation(summary = "Enviar email de verificación", description = "Envía un email de verificación de cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email de verificación enviado"),
            @ApiResponse(responseCode = "500", description = "Error al enviar el email", content = @Content)
    })
    @PostMapping("/verify-email")
    public ResponseEntity<nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.ApiResponse<Void>> sendVerifyEmail(
            @RequestParam String to, @RequestParam String username,
            @RequestParam String verifyLink) {
        emailService.sendVerifyEmail(to, username, verifyLink);
        return ResponseEntity.ok(nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.ApiResponse
                .success("Email de verificación enviado correctamente a " + to));
    }
}
