package bg.softuni.mycinematicketsapp.services.integation;

import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import bg.softuni.mycinematicketsapp.models.events.UserRegisteredEvent;
import bg.softuni.mycinematicketsapp.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserActivationServiceImplIT {

    @Autowired
    private ApplicationEventPublisher publisher;

    @MockBean
    private EmailService emailService;

    @Test
    void testEventPublishingTriggersEmailService() {
        String email = ConstantTest.USER_EMAIL;
        String username = ConstantTest.TEST_USERNAME;
        String token = ConstantTest.TEST_TOKEN;
        UserRegisteredEvent event = new UserRegisteredEvent(this, email, username, token);

        publisher.publishEvent(event);

        verify(emailService, times(1)).sendRegistrationEmail(email, username, token);
    }
}
