package nandoshop.shop.user_service.application.service;

import nandoshop.shop.user_service.application.port.in.command.RegisterUserCommand;
import nandoshop.shop.user_service.application.port.out.UserRepositoryPort;
import nandoshop.shop.user_service.domain.exception.EmailAlreadyRegisteredException;
import nandoshop.shop.user_service.domain.model.User;
import nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegisterUserServiceTest {
    private UserRepositoryPort userRepository;
    private PasswordEncoder passwordEncoder;
    private RegisterUserService registerUserService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepositoryPort.class);
        passwordEncoder = mock(PasswordEncoder.class);
        registerUserService = new RegisterUserService(userRepository, passwordEncoder);
    }

    // Test que verifica el registro exitoso de un usuario
    // Comprueba que se guarde correctamente y se devuelva la respuesta esperada
    @Test
    void registerShouldReturnUserResponseWhenDataIsValid() {
        // Arrange
        RegisterUserCommand command = new RegisterUserCommand(
                "test@example.com",
                "password123",
                "Nando"
        );

        when(userRepository.existsByEmail(command.email())).thenReturn(false);
        when(passwordEncoder.encode(command.rawPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User u = invocation.getArgument(0);
                    return new User(1L, u.getEmail(), u.getPasswordForPersistence(), u.getName());
                });

        // Act
        UserResponse response = registerUserService.register(command);

        // Assert
        assertEquals(1L, response.id());
        assertEquals("test@example.com", response.email());
        assertEquals("Nando", response.name());

        verify(userRepository).existsByEmail(command.email());
        verify(passwordEncoder).encode(command.rawPassword());
        verify(userRepository).save(any(User.class));
    }
    // Test que verifica que se lance una excepción cuando el email ya existe
    // Comprueba que no se guarde el usuario ni se codifique la contraseña
    @Test
    void registerShouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        RegisterUserCommand command = new RegisterUserCommand(
                "test@example.com",
                "password123",
                "Nando"
        );

        when(userRepository.existsByEmail(command.email())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyRegisteredException.class,
                () -> registerUserService.register(command));

        verify(userRepository).existsByEmail(command.email());
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

}
