package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;
import bg.softuni.mycinematicketsapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRegisterController {

    private final UserService userService;

    @Autowired
    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@Valid @RequestBody UserRegisterDto registerDto,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "errors", bindingResult.getAllErrors()
            ));
        }

        userService.registerUser(registerDto);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @GetMapping("/session")
    public ResponseEntity<?> getSessionUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(Map.of("isAuthenticated", false));
        }

        return ResponseEntity.ok(Map.of(
                "isAuthenticated", true,
                "username", principal.getName()
        ));
    }
}