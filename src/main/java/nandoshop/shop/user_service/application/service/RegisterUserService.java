package nandoshop.shop.user_service.application.service;

import nandoshop.shop.user_service.application.port.in.command.RegisterUserCommand;
import nandoshop.shop.user_service.application.port.in.useCase.RegisterUserUseCase;
import nandoshop.shop.user_service.application.port.out.UserRepositoryPort;
import nandoshop.shop.user_service.domain.exception.EmailAlreadyRegisteredException;
import nandoshop.shop.user_service.domain.model.User;
import nandoshop.shop.user_service.domain.service.EmailService;
import nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.UserResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final Argon2PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public RegisterUserService(UserRepositoryPort userRepository, Argon2PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Async
    @Override
    public UserResponse register(RegisterUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyRegisteredException(command.email());
        }

        User user = new User(
                command.email(),
                passwordEncoder.encode(command.rawPassword()),
                command.name()
        );

        User saved = userRepository.save(user);

        String token = generateVerificationToken();
        emailService.sendVerifyEmail(saved.getEmail(), saved.getName(), token);
        return new UserResponse(saved.getId(), saved.getEmail(), saved.getName());
    }

    private String generateVerificationToken() {
        // Logic to generate a secure token
        return java.util.UUID.randomUUID().toString();
    }
}
