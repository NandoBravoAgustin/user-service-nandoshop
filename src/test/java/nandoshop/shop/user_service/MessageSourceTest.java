package nandoshop.shop.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    private MessageSource messageSource;

    @Test
    void testMessageInSpanish() {
        String mensaje = messageSource.getMessage("argon2.hashLength.gt.saltLength", null, new Locale("es"));
        assertEquals("La longitud del hash debe ser mayor que la del salt", mensaje);
    }

    @Test
    void testMessageDefault() {
        String mensaje = messageSource.getMessage("argon2.hashLength.gt.saltLength", null, Locale.getDefault());
        System.out.println("Mensaje por defecto: " + mensaje);
    }

}
