package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRoleRepository;
import bg.softuni.mycinematicketsapp.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void initRoleInDb() {
        if (this.userRoleRepository.count() == 0) {
            List<UserRole> roles = Arrays.stream(UserRoleEnum.values())
                    .map(UserRole::new)
                    .toList();

            this.userRoleRepository.saveAll(roles);
        }
    }

    @Override
    public UserRole getRoleByName(UserRoleEnum name) {
        return this.userRoleRepository.findByRole(name);
    }

    @Override
    public List<UserRole> getAllRoles() {
        return this.userRoleRepository.findAll();
    }
}
