package bg.softuni.mycinematicketsapp.models.enums;

public enum TicketType {

    CHILDREN_UNDER_16("Children under 16", 10.50),
    PUPILS_AND_STUDENTS("Pupils and Students", 12.50),
    PERSONS_OVER_60("People Over 60", 11.50),
    REGULAR("Regular", 15.50);


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
