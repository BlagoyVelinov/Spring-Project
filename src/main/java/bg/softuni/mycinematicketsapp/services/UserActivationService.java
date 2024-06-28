package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.events.UserRegisteredEvent;

public interface UserActivationService {

    void userRegistered(UserRegisteredEvent event);
}
