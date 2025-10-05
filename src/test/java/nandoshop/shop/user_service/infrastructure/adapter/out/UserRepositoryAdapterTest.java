package nandoshop.shop.user_service.infrastructure.adapter.out;

import nandoshop.shop.user_service.domain.model.User;
import nandoshop.shop.user_service.infrastructure.repository.SpringDataUserRepository;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryAdapterTest {
    // Test que verifica que el adaptador guarda un usuario correctamente
    // y convierte entre dominio y entidad
    @Test
    void saveShouldConvertAndReturnDomainUser() {
        SpringDataUserRepository repository = mock(SpringDataUserRepository.class);
        UserRepositoryAdapter adapter = new UserRepositoryAdapter(repository);

        User domainUser = new User("test@example.com", "encodedPass", "Nando");
        UserEntity savedEntity = new UserEntity(1L, "test@example.com", "encodedPass", "Nando");

        when(repository.save(any(UserEntity.class))).thenReturn(savedEntity);

        User result = adapter.save(domainUser);

        assertEquals(savedEntity.getId(), result.getId());
        assertEquals(savedEntity.getEmail(), result.getEmail());
        verify(repository).save(any(UserEntity.class));
    }

    // Test que verifica que existsByEmail delega correctamente al repositorio
    @Test
    void existsByEmailShouldDelegateToRepository() {
        SpringDataUserRepository repository = mock(SpringDataUserRepository.class);
        UserRepositoryAdapter adapter = new UserRepositoryAdapter(repository);

        when(repository.existsByEmail("test@example.com")).thenReturn(true);

        boolean exists = adapter.existsByEmail("test@example.com");

        assertTrue(exists);
        verify(repository).existsByEmail("test@example.com");
    }
}
