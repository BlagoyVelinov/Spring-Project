package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:5173"})
@RequestMapping("/api/home")
public class HomeController {

    private final MovieService movieService;

    @Autowired
    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/about-us")
    public String getAboutUsInfo() {
        return "This is the About Us information for the Cinema Tickets application.";
    }

    @GetMapping("/contact-us")
    public String getContactUsInfo() {
        return "This is the Contact Us information for the Cinema Tickets application.";
    }

    @GetMapping("/4-dx")
    public String get4DxInfo() {
        return "Information about 4DX cinemas.";
    }
    @GetMapping("/imax")
    public String getImaxInfo() {
        return "Information about IMAX cinemas.";
    }

}
