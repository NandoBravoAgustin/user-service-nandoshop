package nandoshop.shop.user_service.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    // Test que verifica que un usuario se cree correctamente
    // con email, nombre y contraseña válidos
    @Test
    void shouldCreateUserWhenDataIsValid() {
        User user = new User("test@example.com", "password123", "Nando");
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Nando", user.getName());
        assertNotNull(user.getPasswordForPersistence());
    }

    // Test que verifica que se lance una excepción
    // cuando el email proporcionado no es válido
    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new User("invalidEmail", "password123", "Nando"));
    }

    // Test que verifica que se lance una excepción
    // cuando la contraseña es demasiado corta
    @Test
    void shouldThrowExceptionWhenPasswordTooShort() {
        assertThrows(IllegalArgumentException.class,
                () -> new User("test@example.com", "123", "Nando"));
    }

    // Test que verifica que se lance una excepción
    // cuando el nombre del usuario está vacío
    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new User("test@example.com", "password123", ""));
    }
}
