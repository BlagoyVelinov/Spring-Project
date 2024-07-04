package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProgramController {


    @GetMapping("/program")
    public String getProgram() {
        return "program";
    }

    public String postProgram() {
        return Constant.REDIRECT_PROGRAM;
    }
}
