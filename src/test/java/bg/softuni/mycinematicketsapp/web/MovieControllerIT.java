package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.entities.MovieClass;
import bg.softuni.mycinematicketsapp.models.enums.Genre;
import bg.softuni.mycinematicketsapp.models.enums.HallNumber;
import bg.softuni.mycinematicketsapp.models.enums.MovieClassEnum;
import bg.softuni.mycinematicketsapp.models.enums.ProjectionFormat;
import bg.softuni.mycinematicketsapp.services.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIT {
    private static final String TEST_USER_ADMIN = "admin";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService mockMovieService;

    @Test
    @WithMockUser(
            username = TEST_USER_ADMIN,
            roles = {"USER", "ADMINISTRATOR"})
    void testGetAddMovie() throws Exception {
        this.mockMvc.perform(get("/movies/add-movie")).andDo(print())
                .andExpect(view().name("add-movie"));
    }

    @Test
    @WithMockUser(
            username = TEST_USER_ADMIN,
            roles = {"USER", "ADMINISTRATOR"})
    void testPostAddMovie() throws Exception {
        this.mockMvc.perform(post("/movies/add-movie")
                        .param("name", "testMovie")
                        .param("movieLength", "120")
                        .param("hallNumber", "HALL_1")
                        .param("audio", "Angl")
                        .param("subtitles", "Bulg")
                        .param("description", "Testing adding the movie")
                        .param("imageUrl", "/images/rent-a-hall.jpg")
                        .param("trailerUrl", "https://www.youtube.com/embed/27qWDMieaNM")
                        .param("projectionFormat", "D_3D")
                        .param("movieClass", "C_")
                        .param("genreCategories", "ADVENTURE", "COMEDY")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Constant.REDIRECT_PROGRAM));

    }

    @Test
    @WithMockUser(
            username = TEST_USER_ADMIN,
            roles = {"USER", "ADMINISTRATOR"})
    void testDeleteMovieById() throws Exception {
        CreateMovieDto createMovie = this.createMovie();
        MovieViewDto movieView = this.mapMovieDtoToMovieView(createMovie);

        this.mockMvc.perform(delete("/movies/delete-movie/{id}", movieView.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Constant.REDIRECT_PROGRAM));

        MovieViewDto actualMovie = this.mockMovieService.getMovieViewById(movieView.getId());
        Assertions.assertNull(actualMovie);
    }

    private CreateMovieDto createMovie() {
        CreateMovieDto movieViewDto = new CreateMovieDto();
        movieViewDto
                .setName("testMovie")
                .setMovieLength(120)
                .setHallNumber(HallNumber.HALL_1)
                .setAudio("Angl")
                .setSubtitles("Bulg")
                .setDescription("Testing adding the movie")
                .setImageUrl("/images/rent-a-hall.jpg")
                .setTrailerUrl("https://www.youtube.com/embed/27qWDMieaNM")
                .setProjectionFormat(ProjectionFormat.D_3D)
                .setMovieClass(MovieClassEnum.C_)
                .setGenreCategories(List.of(Genre.ADVENTURE, Genre.COMEDY));
        this.mockMovieService.movieCreate(movieViewDto);
        return movieViewDto;
    }

    private MovieViewDto mapMovieDtoToMovieView(CreateMovieDto createMovieDto) {
        MovieClass movieClass = new MovieClass(createMovieDto.getMovieClass());
        return new MovieViewDto()
                .setId(1)
                .setName(createMovie().getName())
                .setMovieLength(createMovie().getMovieLength())
                .setHallNumber(createMovieDto.getHallNumber())
                .setAudio(createMovie().getAudio())
                .setSubtitles(createMovie().getSubtitles())
                .setDescription(createMovie().getDescription())
                .setImageUrl(createMovie().getImageUrl())
                .setTrailerUrl(createMovie().getTrailerUrl())
                .setProjectionFormat(createMovieDto.getProjectionFormat())
                .setMovieClass(movieClass)
                .setGenreCategories(createMovieDto.getGenreCategories());
    }

}
