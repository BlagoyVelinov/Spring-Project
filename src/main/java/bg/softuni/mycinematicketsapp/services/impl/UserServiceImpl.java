package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.UserDetailsDto;
import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.UserViewDto;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.models.events.UserRegisteredEvent;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.services.UserRoleService;
import bg.softuni.mycinematicketsapp.services.UserService;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.mycinematicketsapp.constants.ExceptionMessages.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher appEventPublisher;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService,
                           PasswordEncoder passwordEncoder, ModelMapper modelMapper, ApplicationEventPublisher appEventPublisher) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.appEventPublisher = appEventPublisher;
    }

    @Override
    public void registerUser(UserRegisterDto registerDto) {
        UserEntity user = this.mapUserDtoToUser(registerDto);
        this.userRepository.save(user);

        this.appEventPublisher.publishEvent(new UserRegisteredEvent(
                Constant.SOURCE_NAME,
                registerDto.getEmail(),
                registerDto.getName()
        ));
    }

    @Override
    public boolean isAdmin(String username) {
        UserEntity user = this.getUserByUsername(username);

        return user.getRoles()
                .stream()
                .map(UserRole::getRole)
                .anyMatch(role -> UserRoleEnum.ADMINISTRATOR == role);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public UserViewDto getUserDtoByUsername(String username) {
        UserEntity currUser = this.getUserByUsername(username);
        UserViewDto viewDto = this.modelMapper.map(currUser, UserViewDto.class);

        if(this.isAdmin(username)) {
            viewDto.setAdmin(true);
        }

        return viewDto;
    }

    @Override
    public void initAdminUserInDb() {
        if(this.userRepository.count() == 0) {
            List<UserRole> roles = this.userRoleService.getAllRoles();

            UserEntity user = new UserEntity()
                    .setBirthdate(LocalDate.parse(Constant.USER_BIRTHDATE))
                    .setCreated(LocalDateTime.parse(Constant.USER_CREATED))
                    .setEmail(Constant.USER_EMAIL)
                    .setImageUrl(null)
                    .setModified(null)
                    .setName(Constant.USER_NAME)
                    .setPassword(Constant.USER_PASSWORD)
                    .setUsername(Constant.USER_USERNAME)
                    .setRoles(roles);

            this.userRepository.save(user);
        }
    }

    @Override
    public UserDetailsDto getUserDetailsDtoById(long id) {
        UserEntity userEntity = this.getUserById(id);

        return this.modelMapper.map(userEntity, UserDetailsDto.class);
    }

    @Override
    public List<UserViewDto> getAllUserViewDto() {
        List<UserEntity> allUsers = this.userRepository.findAll();
        final int[] count = {0};
        return allUsers.stream()
                .map(user -> {
                    UserViewDto userViewDto = this.modelMapper.map(user, UserViewDto.class);
                    if (count[0] < 1) {
                        count[0]++;
                        userViewDto.setAdmin(this.isAdmin(user.getUsername()));
                    }
                    return userViewDto;
                }).toList();
    }

    @Override
    public UserDetailsDto editUserDetailsDtoById(long id, UserDetailsDto userDetails) {
        UserEntity user = this.getUserById(id);

        user.setModified(LocalDateTime.now())
                .setName(userDetails.getName())
                .setUsername(userDetails.getUsername())
                .setEmail(userDetails.getEmail());

        this.userRepository.save(user);

        return userDetails;
    }

    @Override
    public String editProfilePhotoById(long id, String newImageUrl) {
        UserEntity user = this.getUserById(id);

        user.setImageUrl(newImageUrl);
        userRepository.save(user);

        return Constant.UPDATE_PROFILE_PHOTO;
    }

    @Override
    public boolean deleteCurrentUserById(long id) {
        UserEntity user = this.getUserById(id);

        if (user.isActive()) {
            user.setActive(false);
            return true;
        }
        return false;
    }

    private UserEntity getUserById(long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(USER_NOT_FOUND));
    }

    private UserEntity mapUserDtoToUser(UserRegisterDto registerDto) {
        UserRole roleTypeUser = this.userRoleService.getRoleByName(UserRoleEnum.USER);
        UserEntity user = this.modelMapper
                .map(registerDto, UserEntity.class)
                .setPassword(this.passwordEncoder.encode(registerDto.getPassword()));

        List<UserRole> allRoles = this.userRoleService.getAllRoles();
        List<UserRole> rolesForUser = user.getRoles();
        rolesForUser.add(roleTypeUser);

        user.setRoles(this.userRepository.count() == 0 ? allRoles : rolesForUser);
        return user;
    }
}
