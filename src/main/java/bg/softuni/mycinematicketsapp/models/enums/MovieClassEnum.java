package bg.softuni.mycinematicketsapp.models.enums;

public enum MovieClassEnum {
    B_("/images/icons/B.png"), // Без възрастови ограничения
    C_("/images/icons/C.png"), // Забранено за лица под 12
    C_PLUS("/images/icons/C_PLUS.png"), // Забранено за лица под 14
    D_("/images/icons/D.png"), // Забранено за лица под 16
    X_("/images/icons/X.png"), //Забранено за лица под 18
    TBC("/images/icons/TBC.png"); // Без категория

    private String value;

    MovieClassEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
