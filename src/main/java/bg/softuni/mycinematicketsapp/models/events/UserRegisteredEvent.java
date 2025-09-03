package bg.softuni.mycinematicketsapp.models.events;

import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {

    private final String userEmail;
    private final String usernames;
    private final String activationToken;
    public UserRegisteredEvent(Object source, String userEmail, String usernames, String activationToken) {
        super(source);
        this.userEmail = userEmail;
        this.usernames = usernames;
        this.activationToken = activationToken;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUsernames() {
        return usernames;
    }

    public String getActivationToken() {return  activationToken;}
}
