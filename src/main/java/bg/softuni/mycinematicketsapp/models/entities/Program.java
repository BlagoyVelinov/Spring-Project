package bg.softuni.mycinematicketsapp.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "programs")
public class Program extends BaseEntity {
    @Column(name = "date_of_projection")
    private LocalDate dateOfProjection;

    @OneToMany(mappedBy = "program")
    private Set<City> cities;

    public Program() {
        this.cities = new HashSet<>();
    }

    public LocalDate getDateOfProjection() {
        return dateOfProjection;
    }

    public Program setDateOfProjection(LocalDate dateOfProjection) {
        this.dateOfProjection = dateOfProjection;
        return this;
    }

    public Set<City> getCities() {
        return cities;
    }

    public Program setCities(Set<City> cities) {
        this.cities = cities;
        return this;
    }
}
