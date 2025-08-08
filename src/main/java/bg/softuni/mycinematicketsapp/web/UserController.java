package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.UserDetailsDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.UserViewDto;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserViewDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserDtoByUsername(username));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable long id, Authentication authentication) {
        String currentUsername = authentication.getName();
        UserEntity currentUser = userService.getUserByUsername(currentUsername);


        boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(userRole -> userRole
                        .getRole()
                        .name()
                        .equals(UserRoleEnum.ADMINISTRATOR.name()));

        return ResponseEntity.ok(userService.getUserDetailsDtoById(id));
    }
}
