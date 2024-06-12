package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.entities.MovieClass;
import bg.softuni.mycinematicketsapp.models.enums.MovieClassEnum;

public interface MovieClassService {

    void initMovieClassesInDb();

    MovieClass getMovieClassByName(MovieClassEnum name);
}
