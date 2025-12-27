package nandoshop.shop.user_service.domain.model;

import java.util.regex.Pattern;

public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private boolean emailVerified = false;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    // Para usuarios nuevos
    public User(String email, String password, String name) {
        validateEmail(email);
        validatePassword(password);
        validateName(name);

        this.email = email;
        this.password = password;
        this.name = name;
        this.emailVerified = emailVerified;
    }
    // Para usuarios existentes
    public User(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }

    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }

    public String getPasswordForPersistence() {
        return password;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
