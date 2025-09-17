package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;
import bg.softuni.mycinematicketsapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRegisterController {

    private final UserService userService;
    @Value("${app.redirect_login_uri}")
    private final String redirectUri;
    @Autowired
    public UserRegisterController(UserService userService,
                                  @Value("${app.redirect_login_uri}") String redirectUri) {
        this.userService = userService;
        this.redirectUri = redirectUri;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@Valid @RequestBody UserRegisterDto registerDto,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                    Constant.ERROR, bindingResult.getAllErrors()
            ));
        }

        userService.registerUser(registerDto);

        return ResponseEntity.ok(Map.of(Constant.MESSAGE, Constant.SUCCESS_SIGNUP_USER));
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activateUser(@RequestParam String token) {
        try {
            userService.activateUserByToken(token);

            URI redirectedUri = URI.create(this.redirectUri);

            return ResponseEntity.status(HttpStatus.FOUND).location(redirectedUri).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/session")
    public ResponseEntity<?> getSessionUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(Map.of(Constant.IS_AUTHENTICATED, false));
        }

        return ResponseEntity.ok(Map.of(
                Constant.IS_AUTHENTICATED, true,
                Constant.USERNAME, principal.getName()
        ));
    }
}