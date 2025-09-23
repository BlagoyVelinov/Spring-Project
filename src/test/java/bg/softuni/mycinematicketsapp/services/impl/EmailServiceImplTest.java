package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import bg.softuni.mycinematicketsapp.models.dtos.requests.ContactRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        emailService = new EmailServiceImpl(
                templateEngine,
                javaMailSender,
                ConstantTest.TEST_COMPANY_EMAIL,
                ConstantTest.TEST_ACTIVATION_BASE_URL
        );
        ReflectionTestUtils.setField(emailService, "cinemaTicketsEmail", ConstantTest.TEST_COMPANY_EMAIL);
    }

    @Test
    void testSendRegistrationEmail() {
        MimeMessage mimeMessage = new MimeMessage((Session) null);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq(ConstantTest.TEST_EMAIL_TEMPLATE), any(Context.class)))
                .thenReturn(ConstantTest.TEST_RESPONSE_HTML);

        emailService.sendRegistrationEmail(ConstantTest.USER_EMAIL, ConstantTest.TEST_USERNAME, ConstantTest.TEST_TOKEN);

        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
        verify(templateEngine, times(1)).process(eq(ConstantTest.TEST_EMAIL_TEMPLATE), any(Context.class));
    }

    @Test
    void testSendContactMessage() throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage((Session) null);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        ContactRequest request = new ContactRequest();
        request.setFrom(ConstantTest.USER_EMAIL);
        request.setSubject(ConstantTest.TEST_SUBJECT);
        request.setMessage(ConstantTest.TEST_MESSAGE);

        emailService.sendContactMessage(request);

        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }
}
