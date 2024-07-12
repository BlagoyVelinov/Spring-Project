package bg.softuni.mycinematicketsapp.models.entities;


import bg.softuni.mycinematicketsapp.models.enums.CityName;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "cities")
public class City extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private CityName location;

    @ManyToOne
    private Program program;
    public City() {}

    public City(CityName location) {
        this.location = location;
    }

    public CityName getLocation() {
        return location;
    }

    public City setLocation(CityName location) {
        this.location = location;
        return this;
    }

    public Program getProgram() {
        return program;
    }

    public City setProgram(Program program) {
        this.program = program;
        return this;
    }
}
