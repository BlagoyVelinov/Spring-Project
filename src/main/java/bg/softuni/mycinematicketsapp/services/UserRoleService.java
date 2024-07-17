package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;

import java.util.List;

public interface UserRoleService {

    boolean initRoleInDb();
    UserRole getRoleByName(UserRoleEnum name);

    List<UserRole> getAllRoles();
}
