package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.repository.CityRepository;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    void testInitCitiesNamesInDb_Success() {
        when(cityRepository.count()).thenReturn(0L);

        cityService.initCitiesNamesInDb();

        verify(cityRepository, times(1)).saveAll(anySet());
    }

    @Test
    void testInitCitiesNamesInDb_NotSuccess() {
        when(cityRepository.count()).thenReturn(1L);

        cityService.initCitiesNamesInDb();

        verify(cityRepository, times(0)).saveAll(anySet());
    }

    @Test
    void testGetAllCities() {
        Set<City> cities = cityService.getAllCities();

        Assertions.assertNotNull(cities);
        assertEquals(CityName.values().length, cities.size());
    }

    @Test
    void testGetCityByCityName() {
        City city = new City(CityName.SOFIA);

        Mockito.when(cityRepository.findByLocation(CityName.SOFIA)).thenReturn(city);

        City result = cityService.getCityByCityName(CityName.SOFIA);

        Assertions.assertNotNull(result);
        assertEquals(CityName.SOFIA, result.getLocation());

        Mockito.verify(cityRepository, Mockito.times(1)).findByLocation(CityName.SOFIA);
    }

    @Test
    void testGetCityById_Success() {
        City city = new City(CityName.SOFIA);
        city.setId(1L);

        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        City result = cityService.getCityById(1L);

        Assertions.assertNotNull(result);
        assertEquals(CityName.SOFIA, result.getLocation());
        assertEquals(1L, result.getId());

        Mockito.verify(cityRepository, Mockito.times(1)).findById(city.getId());
    }

    @Test
    void testGetCityById_NotFound() {
        Mockito.when(cityRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> cityService.getCityById(100L));
    }

    @AfterEach
    void tearDown() {
        reset(cityRepository);
    }
}
