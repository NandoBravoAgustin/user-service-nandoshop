package nandoshop.shop.user_service.infrastructure.adapter.in;

import nandoshop.shop.user_service.application.port.in.useCase.SendSupportEmailUseCase;
import nandoshop.shop.user_service.domain.service.EmailService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import nandoshop.shop.user_service.infrastructure.adapter.in.dto.request.PasswordResetRequest;

@RestController
@RequestMapping("/api/v1/support")
public class SupportEmailController {
    private final EmailService emailService;
    private final SendSupportEmailUseCase supportEmailService;

    public SupportEmailController(EmailService emailService, SendSupportEmailUseCase supportEmailService) {
        this.emailService = emailService;
        this.supportEmailService = supportEmailService;
    }

    @PostMapping("/welcome")
    public ResponseEntity<String> sendWelcomeEmail(@RequestParam String to, @RequestParam String username) {
        emailService.sendWelcomeEmail(to, username);
        return ResponseEntity.ok("Email enviado correctamente a " + to);
    }

    @PostMapping("/send-password-reset")
    public ResponseEntity<String> sendPasswordResetEmail(@RequestBody PasswordResetRequest request) {
        supportEmailService.sendPasswordResetEmail(request.email());
        return ResponseEntity.ok("Email de restablecimiento de contraseña enviado correctamente a " + request.email());
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> sendVerifyEmail(@RequestParam String to, @RequestParam String username,
            @RequestParam String verifyLink) {
        emailService.sendVerifyEmail(to, username, verifyLink);
        return ResponseEntity.ok("Email de verificación enviado correctamente a " + to);
    }
}
