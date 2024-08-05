package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {


    private UserRoleServiceImpl userRoleServiceTest = mock(UserRoleServiceImpl.class);
    @Mock
    private UserRoleRepository mockRoleRepository;

    @BeforeEach
    void setUp() {
        this.userRoleServiceTest = new UserRoleServiceImpl(
                this.mockRoleRepository
        );
    }

    @Test
    void testInitRoleInDb() {
        this.initUserRoleRepository();

        Assertions.assertTrue(this.userRoleServiceTest.initRoleInDb());
    }
    @Test
    void testNotInitRoleInDb() {
        this.initUserRoleRepository();

        Assertions.assertTrue(this.userRoleServiceTest.initRoleInDb());

    }

    @Test
    void getRoleByName() {
        this.initUserRoleRepository();

        UserRole actualRole = new UserRole(UserRoleEnum.USER);

        Mockito.when(this.userRoleServiceTest.getRoleByName(UserRoleEnum.USER)).thenReturn(actualRole);

        UserRole expectedRole = new UserRole(UserRoleEnum.USER);

        Assertions.assertNotNull(actualRole);
        Assertions.assertEquals(expectedRole.getRole(), actualRole.getRole());

    }

    @Test
    void getAllRoles() {
        List<UserRole> roles = this.getUserRoles();

        Mockito.when(this.userRoleServiceTest.getAllRoles()).thenReturn(roles);
        List<UserRole> userRoles = this.userRoleServiceTest.getAllRoles();

        Assertions.assertEquals(2, userRoles.size());
        Assertions.assertEquals(userRoles.get(0).getRole(), UserRoleEnum.USER);
        Assertions.assertEquals(userRoles.get(1).getRole(), UserRoleEnum.ADMINISTRATOR);
    }

    private List<UserRole> getUserRoles() {
        return Arrays.stream(UserRoleEnum.values())
                .map(UserRole::new)
                .toList();
    }

    private void initUserRoleRepository() {
        if (this.mockRoleRepository.count() == 0) {
            List<UserRole> roles = Arrays.stream(UserRoleEnum.values())
                    .map(UserRole::new)
                    .toList();

            this.mockRoleRepository.saveAll(roles);
        }
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(this.mockRoleRepository);
    }
}