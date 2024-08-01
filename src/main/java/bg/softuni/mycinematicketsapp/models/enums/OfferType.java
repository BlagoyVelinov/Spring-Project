package bg.softuni.mycinematicketsapp.models.enums;

public enum OfferType {
    CINEMA_OFFERS("Cinema Offers"),
    FOR_THE_SCHOOLS("For the Schools"),
    FOR_THE_BUSINESS("For the Business");

    private String value;

    OfferType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public OfferType setValue(String value) {
        this.value = value;
        return this;
    }
}
