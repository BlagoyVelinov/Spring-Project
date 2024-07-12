package bg.softuni.mycinematicketsapp.testUtils;

import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTestDataUtil {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserEntity createTestUser(String username, String email) {
        return createUser(username, email, List.of(UserRoleEnum.USER));
    }

    public UserEntity createTestAdministrator(String username, String email) {
        return createUser(username, email, List.of(UserRoleEnum.ADMINISTRATOR));

    }

    private UserEntity createUser(String username, String email, List<UserRoleEnum> roles) {
        List<UserRole> allByRole = this.userRoleRepository.findAllByRoleIn(roles);

        UserEntity user = new UserEntity()
                .setActive(true)
                .setUsername(username)
                .setEmail(email)
                .setPassword("123456789")
                .setName("Gohso Goshev")
                .setRoles(allByRole);

        return this.userRepository.save(user);
    }
    public void cleanUp() {
        this.userRepository.deleteAll();
    }
}
