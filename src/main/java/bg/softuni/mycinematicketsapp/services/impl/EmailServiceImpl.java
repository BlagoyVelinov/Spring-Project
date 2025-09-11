package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.requests.ContactRequest;
import bg.softuni.mycinematicketsapp.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final String cinemaTicketEmail;

    @Value("${mail.cinema_tickets}")
    private String cinemaTicketsEmail;

    @Autowired
    public EmailServiceImpl(TemplateEngine templateEngine, JavaMailSender javaMailSender,
                        @Value("${mail.cinema_tickets}") String cinemaTicketEmail) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.cinemaTicketEmail = cinemaTicketEmail;
    }

    @Override
    public void sendRegistrationEmail(String userEmail, String username, String token) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom(this.cinemaTicketEmail);
            mimeMessageHelper.setReplyTo(this.cinemaTicketEmail);
            mimeMessageHelper.setSubject("Welcome to Cinema Tickets!");
            mimeMessageHelper.setText(generateRegistrationEmailBody(username, token), true);


            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendContactMessage(ContactRequest request) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(cinemaTicketsEmail);
        helper.setTo(cinemaTicketsEmail);
        helper.setSubject(request.getSubject());

        String body = "Reply-To: " + request.getFrom() + "\n\n" + request.getMessage();
        helper.setText(body);

        javaMailSender.send(message);
    }

    private String generateRegistrationEmailBody(String username, String token) {
        Context context = new Context();
        context.setVariable("username", username);

        context.setVariable("activationLink", "http://localhost:8080/api/users/activate?token=" + token);

        return this.templateEngine.process("email/registration-email", context);
    }
}
