package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.requests.ContactRequest;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendRegistrationEmail(String userEmail, String usernames, String token);

    void sendContactMessage(ContactRequest request) throws MessagingException;
}
