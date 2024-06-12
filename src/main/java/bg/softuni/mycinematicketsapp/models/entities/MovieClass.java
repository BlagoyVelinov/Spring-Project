package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.MovieClassEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "movie_classes")
public class MovieClass extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private MovieClassEnum name;
    @Column
    private String description;

    public MovieClass() {
    }

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

    public String getDescription() {
        return description;
    }

    public MovieClass setDescription(String description) {
        this.description = description;
        return this;
    }
    private void setDescription(MovieClassEnum name) {
        String description = "";
        switch (name) {
            case A_ -> description = "No age restrictions";
            case B_ -> description = "Forbidden under 12";
            case C_ -> description = "Forbidden under 14";
            case D_ -> description = "Forbidden under 16";
            case X_ -> description = "Forbidden under 18";
            case N_A -> description = "Uncategorized";
        }

        this.description = description;
    }
}
