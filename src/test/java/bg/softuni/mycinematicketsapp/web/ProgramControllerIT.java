package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.entities.BookingTime;
import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.repository.CityRepository;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@SpringBootTest
@AutoConfigureMockMvc
public class ProgramControllerIT {
    private static final String TEST_USER_ADMIN = "admin";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @Test
    void testAllMoviesInProgram() throws Exception {
        this.mockMvc.perform(get("/program")).andDo(print())
                .andExpect(view().name("program"));
    }

    @Test
    @WithMockUser(
            username = TEST_USER_ADMIN,
            roles = {"USER", "ADMINISTRATOR"})
    void testGetUpdateProjection() throws Exception {
        MovieViewDto movieView = this.movieService.getMovieViewById(1);
        this.mockMvc.perform(get("/program/update-projection-time/{id}", movieView.getId()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }
    @Test
    @WithMockUser(
            username = TEST_USER_ADMIN,
            roles = {"USER", "ADMINISTRATOR"})
    void testPutUpdateProjection() throws Exception {
        MovieViewDto movieView = this.movieService.getMovieViewById(1);


        this.mockMvc.perform(put("/program/update-projection-time/{id}", movieView.getId())
                        .param("startMovieTimes", "_11_50")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Constant.REDIRECT_PROGRAM));
        BookingTime bookingTime = movieView.getBookingTimes().get(0);
        Assertions.assertNotNull(bookingTime);
        Assertions.assertEquals("_11_50", bookingTime.getBookingTime().name());
    }
}
