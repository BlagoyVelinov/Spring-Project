package bg.softuni.mycinematicketsapp.models.enums;

public enum TicketType {

    CHILDREN_UNDER_16("Children under 16", 5.37),
    PUPILS_AND_STUDENTS("Pupils and Students", 6.39),
    PERSONS_OVER_60("People Over 60", 5.88),
    REGULAR("Regular", 7.92);


    private String value;
    private double price;

    TicketType(String value, double price) {
        this.price = price;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public double getPrice() {
        return price;
    }
}
