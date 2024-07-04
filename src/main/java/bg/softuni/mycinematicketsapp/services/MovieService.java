package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;

public interface MovieService {
    void movieCreate(CreateMovieDto createMovie);
}
