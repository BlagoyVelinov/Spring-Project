package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.UserDetailsDto;
import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.UserViewDto;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;

import java.util.List;

public interface UserService {
    void registerUser(UserRegisterDto registerDto);

    boolean isAdmin(String username);

    UserEntity getUserByUsername(String username);

    UserViewDto getUserDtoByUsername(String username);

    void initAdminUserInDb();

    UserDetailsDto getUserDetailsDtoById(long id);

    List<UserViewDto> getAllUserViewDto();

    UserDetailsDto editUserDetailsDtoById(long id, UserDetailsDto userDetails);

    String editProfilePhotoById(long id, String userDetails);

    boolean deleteCurrentUserById(long id);
}
