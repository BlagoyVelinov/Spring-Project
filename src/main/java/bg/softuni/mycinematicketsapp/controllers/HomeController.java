package bg.softuni.mycinematicketsapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/program")
    public String program() {
        return "program";
    }
    @GetMapping("/about-us")
    public String aboutUs() {
        return "about-us";
    }

    @GetMapping("/contact-us")
    public String contactUs() {
        return "contact-us";
    }

    @GetMapping("/4-dx")
    public String fourDx() {
        return "4-dx";
    }
    @GetMapping("/imax")
    public String imax() {
        return "imax";
    }

    @GetMapping("/trailer")
    public String trailer() {
        return "trailer";
    }

    @GetMapping("/program/update-projection-time")
    public String updateProjection() {
        return "update-projection-time";
    }

}
