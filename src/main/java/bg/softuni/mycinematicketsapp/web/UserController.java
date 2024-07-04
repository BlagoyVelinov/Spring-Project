package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {


    @ModelAttribute("badCredentials")
    public void badCredentials(Model model) {
        model.addAttribute(Constant.BAD_CREDENTIALS);
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login-error")
    public String errorLogin(@ModelAttribute("username") String username,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes
                .addFlashAttribute("username", username)
                .addFlashAttribute(Constant.BAD_CREDENTIALS, "true");
        return Constant.REDIRECT_LOGIN;
    }

}