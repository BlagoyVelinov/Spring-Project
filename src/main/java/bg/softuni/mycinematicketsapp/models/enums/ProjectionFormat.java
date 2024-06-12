package bg.softuni.mycinematicketsapp.models.enums;

public enum ProjectionFormat {
    D_2D("2D"),
    D_3D("3D"),
    D_4DX("4DX");

    private String value;

    ProjectionFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
