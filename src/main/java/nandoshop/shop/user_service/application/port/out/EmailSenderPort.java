package nandoshop.shop.user_service.application.port.out;

import nandoshop.shop.user_service.domain.model.Email;

public interface EmailSenderPort {
    void sendEmail(Email email);
}
