package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.entities.City;

import java.time.LocalDate;

public class ProgramViewDto {
    private long id;

    private LocalDate dateOfProjection;

    private City city;

    public long getId() {
        return id;
    }

    public ProgramViewDto setId(long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDateOfProjection() {
        return dateOfProjection;
    }

    public ProgramViewDto setDateOfProjection(LocalDate dateOfProjection) {
        this.dateOfProjection = dateOfProjection;
        return this;
    }

    public City getCity() {
        return city;
    }

    public ProgramViewDto setCity(City city) {
        this.city = city;
        return this;
    }

}
