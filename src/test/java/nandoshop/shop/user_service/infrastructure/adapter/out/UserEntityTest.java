package nandoshop.shop.user_service.infrastructure.adapter.out;

import nandoshop.shop.user_service.domain.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {
    // Test que verifica que la conversión de entidad a dominio funcione
    // correctamente
    @Test
    void shouldConvertToDomainCorrectly() {
        UserEntity entity = new UserEntity(1L, "test@example.com", "pass", "Nando", false);
        User domain = entity.toDomain();

        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getEmail(), domain.getEmail());
        assertEquals(entity.getName(), domain.getName());
    }

    // Test que verifica que la conversión de dominio a entidad funcione
    // correctamente
    @Test
    void shouldConvertFromDomainCorrectly() {
        User domain = new User(1L, "test@example.com", "pass", "Nando");
        UserEntity entity = UserEntity.fromDomain(domain);

        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getEmail(), entity.getEmail());
        assertEquals(domain.getName(), entity.getName());
    }
}
