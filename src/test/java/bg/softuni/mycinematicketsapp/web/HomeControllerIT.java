package bg.softuni.mycinematicketsapp.web;

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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService mockMovieService;
    @Test
    void testGetHomePage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(view().name("index"));
    }
    @Test
    void testAboutUs() throws Exception {
        this.mockMvc.perform(get("/about-us")).andDo(print())
                .andExpect(view().name("about-us"));
    }
    @Test
    void testContactUs() throws Exception {
        this.mockMvc.perform(get("/contact-us")).andDo(print())
                .andExpect(view().name("contact-us"));
    }
    @Test
    void testFourDx() throws Exception {
        this.mockMvc.perform(get("/4-dx")).andDo(print())
                .andExpect(view().name("4-dx"));
    }
    @Test
    void testImax() throws Exception {
        this.mockMvc.perform(get("/imax")).andDo(print())
                .andExpect(view().name("imax"));
    }
    @Test
    void testGetTrailer() throws Exception {
        CreateMovieDto createMovie = this.createMovie();
        MovieViewDto movie = this.mapMovieDtoToMovieView(createMovie);

        this.mockMvc.perform(get("/trailer/{id}", movie.getId()))
                .andDo(print())
                .andExpect(view().name("trailer"));

        MovieViewDto actualMovie = this.mockMovieService.getMovieViewById(movie.getId());

        Assertions.assertEquals("https://www.youtube.com/embed/27qWDMieaNM", actualMovie.getTrailerUrl());

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
