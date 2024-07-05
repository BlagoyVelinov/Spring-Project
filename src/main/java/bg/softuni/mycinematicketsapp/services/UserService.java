package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;

public interface UserService {
    void registerUser(UserRegisterDto registerDto);

}
