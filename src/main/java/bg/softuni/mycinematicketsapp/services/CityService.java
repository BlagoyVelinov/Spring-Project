package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.enums.CityName;

import java.util.List;
import java.util.Set;

public interface CityService {
    void initCitiesNamesInDb();

    Set<City> getAllCities();

    City getCityByCityName(CityName cityName);

    City getCityById(long id);
}
