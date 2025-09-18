package bg.softuni.mycinematicketsapp.services.integation;
import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.repository.CityRepository;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import bg.softuni.mycinematicketsapp.services.impl.CityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CityServiceImpl.class)
class CityServiceImplIT {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityServiceImpl cityService;

    @BeforeEach
    void setUp() {
        cityRepository.deleteAll();
    }

    @Test
    void testInitCitiesNamesInDb_ShouldPopulateDb_WhenEmpty() {
        assertEquals(0, cityRepository.count());

        cityService.initCitiesNamesInDb();

        long expected = CityName.values().length;
        assertEquals(expected, cityRepository.count());
    }

    @Test
    void testInitCitiesNamesInDb_ShouldNotDuplicate_WhenAlreadyExists() {
        cityService.initCitiesNamesInDb();
        long countBefore = cityRepository.count();

        cityService.initCitiesNamesInDb();
        long countAfter = cityRepository.count();

        assertEquals(countBefore, countAfter);
    }

    @Test
    void testGetAllCities_ShouldReturnAllEnums() {
        cityService.initCitiesNamesInDb();

        Set<City> cities = cityService.getAllCities();

        assertEquals(CityName.values().length, cities.size());
        assertTrue(cities.stream().anyMatch(c -> c.getLocation() == CityName.SOFIA));
    }

    @Test
    void testGetCityByCityName_ShouldReturnCorrectCity() {
        cityService.initCitiesNamesInDb();

        City city = cityService.getCityByCityName(CityName.VARNA);

        assertNotNull(city);
        assertEquals(CityName.VARNA, city.getLocation());
    }

    @Test
    void testGetCityById_ShouldReturnCity_WhenExists() {
        cityService.initCitiesNamesInDb();
        City sofia = cityRepository.findByLocation(CityName.SOFIA);

        City found = cityService.getCityById(sofia.getId());

        assertEquals(CityName.SOFIA, found.getLocation());
    }

    @Test
    void testGetCityById_ShouldThrow_WhenNotExists() {
        assertThrows(ObjectNotFoundException.class,
                () -> cityService.getCityById(999L));
    }
}

