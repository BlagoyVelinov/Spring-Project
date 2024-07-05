package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.ProgramViewDto;
import bg.softuni.mycinematicketsapp.models.enums.StartProjectionTimeEnum;
import bg.softuni.mycinematicketsapp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Set;

@Controller
public class ProgramController {

    private final MovieService movieService;

    @Autowired
    public ProgramController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/program")
    public String getProgram() {
        ProgramViewDto programViewDto = new ProgramViewDto();
        Set<MovieViewDto> allMoviesView = this.movieService.getAllMoviesView();
        programViewDto.setMovies(allMoviesView);
        return "program";
    }

    public String postProgram() {
        return Constant.REDIRECT_PROGRAM;
    }
}
