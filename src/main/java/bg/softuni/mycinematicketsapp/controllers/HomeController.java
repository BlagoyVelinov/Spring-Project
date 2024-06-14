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

}
