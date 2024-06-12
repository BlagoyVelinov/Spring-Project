package bg.softuni.mycinematicketsapp.models.enums;

public enum TicketType {

    CHILDREN_DURING_THE_WEEK("CHILDREN DURING THE WEEK"),
    PUPILS_AND_STUDENTS_DURING_THE_WEEK("PUPILS AND STUDENTS DURING THE WEEK"),
    PERSONS_60_DURING_THE_WEEK("PERSONS 60 DURING THE WEEK"),
    REGULAR_DURING_THE_WEEK("REGULAR DURING THE WEEK"),
    CHILDREN_WEEKEND("CHILDREN WEEKEND"),
    PUPILS_AND_STUDENTS_WEEKEND("PUPILS AND STUDENTS WEEKEND"),
    PERSONS_60_WEEKEND("PERSONS 60 WEEKEND"),
    REGULAR__WEEKEND("REGULAR WEEKEND");

    private String value;

    TicketType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
