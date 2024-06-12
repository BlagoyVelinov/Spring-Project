package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private Genre name;

    public Category() {
    }

    public Category(Genre name) {
        this.name = name;
    }

    public Genre getName() {
        return name;
    }

    public Category setName(Genre name) {
        this.name = name;
        return this;
    }
}
