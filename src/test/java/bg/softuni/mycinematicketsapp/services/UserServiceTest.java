package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.repository.UserRoleRepository;
import bg.softuni.mycinematicketsapp.services.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    private UserServiceImpl userServiceTest;

    @Mock
    private UserRepository userRepositoryTest;

    @Mock
    private UserRoleRepository userRoleRepositoryTest;

    @BeforeEach
    void setUp() {
        if (this.userRepositoryTest.count() > 0) {
            this.userRepositoryTest.deleteAll();
        }
    }

//    @AfterEach
//    void rearDown() {
//        this.userRepositoryTest.deleteAll();
//    }

    @Test
    void testCreateUser() {
        UserEntity testUser = this.createTestUser();
//        when(this.userRepositoryTest.findByUsername(testUser.getUsername()))
//                .thenReturn(Optional.of(testUser));

        UserEntity userEntity = this.userRepositoryTest.save(testUser);
        assertNotNull(userEntity);

    }

    private UserEntity createTestUser() {
        return new UserEntity()
                .setUsername("firstLast")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setActive(false)
                .setPassword("123456")
                .setRoles(List.of(
                        new UserRole().setRole(UserRoleEnum.ADMINISTRATOR),
                        new UserRole().setRole(UserRoleEnum.USER)
                ));
    }

}
