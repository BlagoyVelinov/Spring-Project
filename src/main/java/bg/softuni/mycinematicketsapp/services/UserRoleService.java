package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;

public interface UserRoleService {

    void initRoleInDb();
    UserRole getRoleByName(UserRoleEnum name);
}
