package bg.softuni.mycinematicketsapp.models.enums;

public enum BookingTimeEnum {
    _10_20("10:20"),
    _11_50("11:50"),
    _12_20("12:20"),
    _13_50("13:50"),
    _14_20("14:20"),
    _15_50("15:50"),
    _16_20("16:20"),
    _17_50("17:50"),
    _18_20("18:20"),
    _19_50("19:50"),
    _20_20("20:20"),
    _20_50("20:50");

    private String value;

    BookingTimeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public BookingTimeEnum setValue(String value) {
        this.value = value;
        return this;
    }
}
