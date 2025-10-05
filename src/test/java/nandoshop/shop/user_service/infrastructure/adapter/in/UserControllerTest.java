package nandoshop.shop.user_service.infrastructure.adapter.in;

import nandoshop.shop.user_service.application.port.in.useCase.RegisterUserUseCase;
import nandoshop.shop.user_service.infrastructure.adapter.in.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.MockConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    static class MockConfig {
        @Bean
        RegisterUserUseCase registerUserUseCase() {
            return Mockito.mock(RegisterUserUseCase.class);
        }
    }

    // Test que verifica que el endpoint de registro funciona correctamente
    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        when(registerUserUseCase.register(any()))
                .thenReturn(new UserResponse(1L, "test@example.com", "Nando"));

        String requestJson = """
                {
                    "email": "test@example.com",
                    "password": "password123",
                    "name": "Nando"
                }
                """;

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Nando"));
    }

    // Test que verifica que el endpoint devuelve BadRequest cuando la validación falla
    @Test
    void shouldReturnBadRequestWhenValidationFails() throws Exception {
        String invalidJson = """
                {
                    "email": "invalid",
                    "password": "short",
                    "name": ""
                }
                """;

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}