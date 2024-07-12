package bg.softuni.mycinematicketsapp.models.enums;

public enum CityName {
    SOFIA("Sofia"),
    PLOVDIV("Plovdiv"),
    STARA_ZAGORA("Stara Zagora"),
    RUSE("Ruse"),
    BURGAS("Burgas"),
    VARNA("Varna");

    private String value;

    CityName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
