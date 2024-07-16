package bg.softuni.mycinematicketsapp.testutils;

import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("UserTestDataUtils")
public class UserTestDataUtil {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserEntity createTestUser(String username, String email) {
        return createUser(username, email, List.of(UserRoleEnum.USER));
    }
    public UserEntity createTestAdmin(String username, String email) {
        return createUser(username, email, List.of(UserRoleEnum.ADMINISTRATOR));
    }

    public void cleanUp() {
        this.userRepository.deleteAll();
    }

    private UserEntity createUser(String username, String email, List<UserRoleEnum> roles) {
        initUserRoleRepository();

        List<UserRole> allByRole = this.userRoleRepository.findAllByRoleIn(roles);

        UserEntity user = new UserEntity()
                .setActive(true)
                .setUsername(username)
                .setEmail(email)
                .setName("Gosho Goshev")
                .setPassword("123456")
                .setRoles(allByRole);

        return this.userRepository.save(user);

    }

    private void initUserRoleRepository(){
        if (this.userRoleRepository.count() == 0) {
            this.userRoleRepository.saveAll(List.of(
                    new UserRole().setRole(UserRoleEnum.USER),
                    new UserRole().setRole(UserRoleEnum.ADMINISTRATOR)
            ));
        }
    }
}
