package nandoshop.shop.user_service.infrastructure.adapter.in;

import nandoshop.shop.user_service.domain.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/support")
public class SupportEmailController {
    private final EmailService emailService;

    public SupportEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/welcome")
    public ResponseEntity<String> sendWelcomeEmail(@RequestParam String to, @RequestParam String username) {
        emailService.sendWelcomeEmail(to, username);
        return ResponseEntity.ok("Email enviado correctamente a " + to);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> sendPasswordResetEmail(@RequestParam String to, @RequestParam String username, @RequestParam String resetLink) {
        emailService.sendPasswordResetEmail(to, username, resetLink);
        return ResponseEntity.ok("Email de restablecimiento de contraseña enviado correctamente a " + to);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> sendVerifyEmail(@RequestParam String to, @RequestParam String username, @RequestParam String verifyLink) {
        emailService.sendVerifyEmail(to, username, verifyLink);
        return ResponseEntity.ok("Email de verificación enviado correctamente a " + to);
    }
}
