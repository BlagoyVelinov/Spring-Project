package bg.softuni.mycinematicketsapp.models.dtos.requests;

public class ContactRequest {

    private String from;
    private String subject;
    private String message;

    public String getFrom() {
        return from;
    }

    public ContactRequest setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public ContactRequest setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ContactRequest setMessage(String message) {
        this.message = message;
        return this;
    }
}
