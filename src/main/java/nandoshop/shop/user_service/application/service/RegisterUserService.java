package nandoshop.shop.user_service.application.service;

import nandoshop.shop.user_service.application.port.in.command.RegisterUserCommand;
import nandoshop.shop.user_service.application.port.in.useCase.RegisterUserUseCase;
import nandoshop.shop.user_service.application.port.out.UserRepositoryPort;
import nandoshop.shop.user_service.domain.exception.EmailAlreadyRegisteredException;
import nandoshop.shop.user_service.domain.model.User;
import nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserService(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

        return new UserResponse(saved.getId(), saved.getEmail(), saved.getName());
    }
}
