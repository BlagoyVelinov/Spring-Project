package bg.softuni.mycinematicketsapp.models.enums;

public enum HallNumber {
    HALL_1("1"),
    HALL_2("2"),
    HALL_3("3"),
    HALL_4("4"),
    HALL_5("5"),
    HALL_6("6"),
    HALL_7("7"),
    HALL_8("8");
    private String value;

    HallNumber(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
