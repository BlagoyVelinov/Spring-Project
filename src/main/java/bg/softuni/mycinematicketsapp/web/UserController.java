package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.ExceptionMessages;
import bg.softuni.mycinematicketsapp.models.dtos.UserDetailsDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.UserViewDto;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
        validateCurrentUser(id, authentication);

        return ResponseEntity.ok(userService.getUserDetailsDtoById(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserDetailsDto> editUserById(@PathVariable long id,
                                                       Authentication authentication,
                                                       @Valid @RequestBody UserDetailsDto userDetails) {
        validateCurrentUser(id, authentication);

        return ResponseEntity.ok(userService.editUserDetailsDtoById(id, userDetails));
    }

    @PutMapping("/user/{id}/profile-image")
    public ResponseEntity<?> editProfileImageById(@PathVariable long id,
                                                  Authentication authentication,
                                                  @RequestBody String imageUrl) {
        validateCurrentUser(id, authentication);

        String response = userService.editProfilePhotoById(id, imageUrl);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deactivateUserById(@PathVariable long id, Authentication authentication) {
        validateCurrentUser(id, authentication);

        boolean isDeleted = userService.deactivateCurrentUserById(id);

        if (isDeleted) {
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "User could not be deleted or is not active"));
        }
    }

    private void validateCurrentUser(long id, Authentication authentication) {
        String currentUsername = authentication.getName();
        UserEntity currentUser = userService.getUserByUsername(currentUsername);

        boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(userRole -> userRole
                        .getRole()
                        .name()
                        .equals(UserRoleEnum.ADMINISTRATOR.name()));

        if (!isAdmin && currentUser.getId() != id) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ExceptionMessages.NOT_ADMIN_RIGHTS);
        }
    }
}
