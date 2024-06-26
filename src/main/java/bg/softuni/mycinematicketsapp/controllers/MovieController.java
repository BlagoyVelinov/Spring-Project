package bg.softuni.mycinematicketsapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movies")
public class MovieController {


    @GetMapping("/add-movie")
    public String getAddMovie() {
        return "add-movie";
    }
}
