package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.UserDetailsDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.UserViewDto;
import bg.softuni.mycinematicketsapp.services.SecurityService;
import bg.softuni.mycinematicketsapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/all-users")
    public ResponseEntity<List<UserViewDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUserViewDto());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserViewDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserDtoByUsername(username));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable long id, Authentication authentication) {
       securityService.validateCurrentUser(id, authentication);

        return ResponseEntity.ok(userService.getUserDetailsDtoById(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserDetailsDto> editUserById(@PathVariable long id,
                                                       Authentication authentication,
                                                       @Valid @RequestBody UserDetailsDto userDetails) {
        securityService.validateCurrentUser(id, authentication);

        return ResponseEntity.ok(userService.editUserDetailsDtoById(id, userDetails));
    }

    @PutMapping("/user/{id}/profile-image")
    public ResponseEntity<?> editProfileImageById(@PathVariable long id,
                                                  Authentication authentication,
                                                  @RequestBody String imageUrl) {
        securityService.validateCurrentUser(id, authentication);

        String response = userService.editProfilePhotoById(id, imageUrl);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deactivateUserById(@PathVariable long id, Authentication authentication) {
        securityService.validateCurrentUser(id, authentication);

        boolean isDeleted = userService.deactivateCurrentUserById(id);

        if (isDeleted) {
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "User could not be deleted or is not active"));
        }
    }
}
