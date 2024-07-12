package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.entities.MovieClass;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.models.enums.MovieClassEnum;
import bg.softuni.mycinematicketsapp.repository.CityRepository;
import bg.softuni.mycinematicketsapp.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void initCitiesNamesInDb() {
        if (this.cityRepository.count() == 0) {
            Set<City> citiesList = this.getAllCities();
            this.cityRepository.saveAll(citiesList);
        }
    }

    @Override
    public Set<City> getAllCities() {
        return Arrays.stream(CityName.values())
                .map(City::new)
                .collect(Collectors.toSet());
    }

    @Override
    public City getCityByCityName(CityName location) {
        return this.cityRepository.findByLocation(location);
    }


}
