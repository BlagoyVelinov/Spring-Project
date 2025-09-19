package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
}
