package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.MovieClassEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "movie_classes")
public class MovieClass extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private MovieClassEnum name;
    @Column
    private String icon;
    @Column
    private String description;

    public MovieClass() {}

    public MovieClass(MovieClassEnum name) {
        this.setName(name);
    }

    public MovieClassEnum getName() {
        return name;
    }

    public MovieClass setName(MovieClassEnum name) {
        this.name = name;
        this.setDescription(name);
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public MovieClass setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MovieClass setDescription(String description) {
        this.description = description;
        return this;
    }
    private void setDescription(MovieClassEnum name) {
        String description = "";
        String icon = "";
        switch (name) {
            case B_ -> {
                description = "No age restrictions";
                icon = MovieClassEnum.B_.getValue();
            }
            case C_ -> {
                description = "Forbidden under 12";
                icon = MovieClassEnum.C_.getValue();
            }
            case C_PLUS -> {
                description = "Forbidden under 14";
                icon = MovieClassEnum.C_PLUS.getValue();
            }
            case D_ -> {
                description = "Forbidden under 16";
                icon = MovieClassEnum.D_.getValue();
            }
            case X_ -> {
                description = "Forbidden under 18";
                icon = MovieClassEnum.X_.getValue();
            }
            case TBC -> {
                description = "Uncategorized";
                icon = MovieClassEnum.TBC.getValue();
            }
        }

        this.description = description;
        this.icon = icon;
    }
}
