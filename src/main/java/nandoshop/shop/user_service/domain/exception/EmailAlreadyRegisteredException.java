package nandoshop.shop.user_service.domain.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{
    public EmailAlreadyRegisteredException(String email) {
        super("El email ya está registrado: " + email);
    }
}
