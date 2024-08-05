package bg.softuni.mycinematicketsapp.services.session;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

import static bg.softuni.mycinematicketsapp.constants.ExceptionMessages.USERNAME_NOT_EXIST;

public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(this::mapUserEntityToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_EXIST, username)));
    }

    private UserDetails mapUserEntityToUserDetails(UserEntity currUser) {
        return User.withUsername(currUser.getUsername())
                .password(currUser.getPassword())
                .authorities(currUser.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(Constant.ROLE_ + role.getRole().name()))
                        .collect(Collectors.toList()))
                .build();
    }
}
