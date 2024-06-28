package bg.softuni.mycinematicketsapp.config;

import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.services.impl.MyUserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    private final String rememberMeKey;

    public SpringSecurityConfig(@Value("remember.me.key") String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }

    /**
     * Define which urls are visible by which users
     * <p>
     * All static resources which are situated in js, images, css are available for anyone
     * requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
     * <p>
     * Allow anyone to see the home page, the registration and login pages
     * requestMatchers("/", "/users/register", "/users/login","/users/login-error").permitAll()
     * <p>
     * All other request are authenticated.
     * anyRequest().authenticated()
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/users/register", "/users/login", "/users/login-error").permitAll()
                        .requestMatchers("/offers/all-offers", "/programs/all-programs", "/trailer").permitAll()
                        .requestMatchers("/program", "/4-dx", "/imax", "/about-us", "/contact-us").permitAll()
                        .requestMatchers(HttpMethod.GET, "/offers/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/programs/add-movie", "/offers/add-offer","/program/update-projection-time")
                        .hasRole(UserRoleEnum.ADMINISTRATOR.name())
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> {
                    // Redirect here when we access something which is not allowed,
                    // also this is the page where we perform login.
                    formLogin.loginPage("/users/login")
                            // The name of the input field (in our case in login.html)
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureForwardUrl("/users/login-error");
                }

        ).logout(
                logout -> {
                    // The URL where we should POST something in order to preform the logout
                    logout.logoutUrl("/users/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        );

        //TODO: Remember me!
        return httpSecurity.rememberMe(
                rememberMe -> {
                    rememberMe.key(rememberMeKey)
                            .rememberMeParameter("rememberMe")
                            .rememberMeCookieName("rememberMe");
                }
        ).build();
    }

    /**
     * This service translate the CinemaTickets users and roles
     * to representation which spring security understand.
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new MyUserDetailService(userRepository);
    }

    /**
     * Implement which encoder to use Spring Security
     * for this application
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
