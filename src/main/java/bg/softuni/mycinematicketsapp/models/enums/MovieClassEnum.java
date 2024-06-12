package bg.softuni.mycinematicketsapp.models.enums;

public enum MovieClassEnum {
    A_("A"), // Без възрастови ограничения
    B_("B"), // Забранено за лица под 12
    C_("C"), // Забранено за лица под 14
    D_("D"), // Забранено за лица под 16
    X_("X"), //Забранено за лица под 18
    N_A("N/A"); // Без категория

    private String value;

    MovieClassEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
