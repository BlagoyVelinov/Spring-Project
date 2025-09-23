package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import bg.softuni.mycinematicketsapp.models.events.UserRegisteredEvent;
import bg.softuni.mycinematicketsapp.services.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserActivationServiceImplTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserActivationServiceImpl userActivationService;

    @Test
    void testUserRegisteredEventTriggersEmail() {
        String email = ConstantTest.USER_EMAIL;
        String username = ConstantTest.TEST_USERNAME;
        String token = ConstantTest.TEST_TOKEN;
        UserRegisteredEvent event = new UserRegisteredEvent(this, email, username, token);

        userActivationService.userRegistered(event);

        verify(emailService, times(1)).sendRegistrationEmail(email, username, token);
    }
}
