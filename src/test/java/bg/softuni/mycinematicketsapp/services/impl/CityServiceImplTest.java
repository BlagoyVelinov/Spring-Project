package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.repository.CityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

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

    @AfterEach
    void tearDown() {
        reset(cityRepository);
    }
}
