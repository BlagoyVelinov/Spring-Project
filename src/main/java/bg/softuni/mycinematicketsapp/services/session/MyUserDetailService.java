package bg.softuni.mycinematicketsapp.services.session;

import bg.softuni.mycinematicketsapp.config.SecurityUserDetails;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        return new SecurityUserDetails(currUser);
    }
}
