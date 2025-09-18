package nandoshop.shop.user_service.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Argon2PropertiesValidationTest {
    private final Validator validator;

    public Argon2PropertiesValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidConfiguration() {
        Argon2Properties props = new Argon2Properties(16, 32, 2, 65536, 4);
        Set violations = validator.validate(props);
        assertTrue(violations.isEmpty(), "No debe haber violaciones de validación");
    }

    @Test
    void testInvalidSaltLength() {
        Argon2Properties props = new Argon2Properties(4, 32, 2, 65536, 4);
        Set violations = validator.validate(props);
        assertEquals(1, violations.size());
    }

    @Test
    void testHashSmallerThanSalt() {
        Argon2Properties props = new Argon2Properties(32, 16, 2, 65536, 4);
        Set violations = validator.validate(props);
        assertEquals(1, violations.size(), "Debe fallar la validación hash > salt");
    }

    @Test
    void testMemoryUsageExceedsLimit() {
        Argon2Properties props = new Argon2Properties(16, 32, 2, 524_288, 3); // 1.5GB
        Set violations = validator.validate(props);
        assertEquals(1, violations.size(), "Debe fallar la validación memory*iterations <= 1GB");
    }

    @Test
    void testParallelismExceedsCores() {
        int cores = Runtime.getRuntime().availableProcessors();
        Argon2Properties props = new Argon2Properties(16, 32, cores + 1, 65536, 4);
        Set violations = validator.validate(props);
        assertEquals(1, violations.size(), "Debe fallar la validación de paralelismo máximo");
    }
}
