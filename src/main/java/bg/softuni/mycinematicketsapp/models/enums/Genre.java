package bg.softuni.mycinematicketsapp.models.enums;

public enum Genre {
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    COMEDY("Comedy"),
    FAMILY("Family"),
    HORROR("Horror"),
    ACTION("Action"),
    FANTASY("Fantazy"),
    MYSTERY("Mistery"),
    BULGARIAN("Bulgarian"),
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
