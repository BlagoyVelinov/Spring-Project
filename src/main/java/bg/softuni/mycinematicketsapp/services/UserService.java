package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.UserViewDto;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;

public interface UserService {
    void registerUser(UserRegisterDto registerDto);

    boolean isAdmin(String username);

    UserEntity getUserByUsername(String username);

    UserViewDto getUserDtoByUsername(String username);

    void initAdminUserInDb();
}
