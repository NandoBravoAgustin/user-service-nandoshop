package nandoshop.shop.user_service.infrastructure.adapter.out;

import jakarta.persistence.*;
import lombok.*;
import nandoshop.shop.user_service.domain.model.User;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;

    // ✅ Constructor vacío necesario para JPA
    public UserEntity() {}

    // ✅ Constructor con todos los campos (útil para conversiones)
    public UserEntity(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // 🔄 Conversión al dominio
    public User toDomain() {
        return new User(id, email, password, name);
    }

    // 🔄 Conversión desde el dominio
    public static UserEntity fromDomain(User user) {
        return new UserEntity(
                user.getId(),
                user.getEmail(),
                user.getPasswordForPersistence(),
                user.getName()
        );
    }
}
