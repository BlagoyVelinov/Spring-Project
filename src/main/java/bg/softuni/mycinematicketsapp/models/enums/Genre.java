package bg.softuni.mycinematicketsapp.models.enums;

public enum Genre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    BULGARIAN("Bulgarian"),
    COMEDY("Comedy"),
    FAMILY("Family"),
    FANTASY("Fantasy"),
    HORROR("Horror"),
    MYSTERY("Mystery"),
    ROMANTIC("Romantic"),
    THRILLER("Thriller");

    private String value;

    Genre(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
