package bg.softuni.mycinematicketsapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @GetMapping
    public String offers(){
        return "offers";
    }
}
