package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:5173"})
@RequestMapping("/api/home")
public class HomeController {


    @GetMapping("/about-us")
    public String getAboutUsInfo() {
        return Constant.ABOUT_US;
    }

    @GetMapping("/contact-us")
    public String getContactUsInfo() {
        return Constant.CONTACT_US;
    }

    @GetMapping("/4-dx")
    public String get4DxInfo() {
        return Constant.FOUR_DX;
    }
    @GetMapping("/imax")
    public String getImaxInfo() {
        return Constant.IMAX;
    }

}
