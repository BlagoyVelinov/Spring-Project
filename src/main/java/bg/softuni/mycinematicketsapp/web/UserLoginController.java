package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.config.SecurityUserDetails;
import bg.softuni.mycinematicketsapp.models.dtos.helpers.LoginRequest;
import bg.softuni.mycinematicketsapp.models.dtos.helpers.LoginResponse;
import bg.softuni.mycinematicketsapp.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserLoginController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public UserLoginController(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest body) {

        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword()));

            SecurityUserDetails principal = (SecurityUserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(principal);

            return ResponseEntity.ok(
                    new LoginResponse(principal.getId(),
                            principal.getUsername(),
                            token,
                            principal.isAdmin()
                    ));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("badCredentials", true));
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }
}