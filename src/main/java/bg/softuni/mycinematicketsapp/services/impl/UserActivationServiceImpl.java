package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.events.UserRegisteredEvent;
import bg.softuni.mycinematicketsapp.services.EmailService;
import bg.softuni.mycinematicketsapp.services.UserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserActivationServiceImpl implements UserActivationService {

    private final EmailService emailService;

    @Autowired
    public UserActivationServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @EventListener(UserRegisteredEvent.class)
    public void userRegistered(UserRegisteredEvent event) {
        this.emailService.sendRegistrationEmail(event.getUserEmail(), event.getUsernames());
    }
}
