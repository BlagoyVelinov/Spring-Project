package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.constants.ExceptionMessages;
import bg.softuni.mycinematicketsapp.models.dtos.requests.ContactRequest;
import bg.softuni.mycinematicketsapp.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final EmailService emailService;
    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> sendContactEmail(@RequestBody ContactRequest request) {

        try {
            emailService.sendContactMessage(request);

            return ResponseEntity.ok().body(Constant.SUCCESS_SEND_MESSAGE);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(ExceptionMessages.SEND_MESSAGE_FAILED + e.getMessage());
        }
    }
}
