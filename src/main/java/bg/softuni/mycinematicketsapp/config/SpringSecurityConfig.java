package bg.softuni.mycinematicketsapp.config;

import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.services.session.MyUserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SpringSecurityConfig {

    @Value("${spring.cinema_tickets.remember.me.key}")
    private String rememberMeKey;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOriginPatterns(List.of("http://localhost:*"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(
                                "/", "/users/register", "/users/login", "/users/login-error",
                                "/api/users/register", "/api/users/login", "/api/users/session",
                                "/movies", "/movies/upcoming", "/api/program", "/movies/{id}"
                        ).permitAll()
                        .requestMatchers("/api/order", "/api/order/buy-tickets/{id}", "/api/order/select-seats",
                                "/api/program/order-tickets","/api/user/{username}").authenticated()
                        .requestMatchers("/api/order/buy-tickets/{id}", "/api/order/confirm-order/**", "/api/order/show-tickets/**").authenticated()
                        .requestMatchers("/error", "/test-template/{orderNumber}").permitAll()
                        .requestMatchers("/movies/add-movie", "/api/offers/add-offer",
                                "/api/program/update-projection-time/{id}", "/api/offers/delete-offer/{id}")
                        .hasRole(UserRoleEnum.ADMINISTRATOR.name())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form // 4) Form-login, but for REST login
                        .loginPage("/users/login")
                        .loginProcessingUrl("/api/users/login") // important!
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .failureUrl("/users/login-error")
                )
                .logout(logout -> logout
                        .logoutUrl("/api/users/logout") // if using REST logout
                        .logoutSuccessUrl("/")
                )
                .rememberMe(rm -> rm // 5) Remember-me
                        .key(rememberMeKey)
                        .rememberMeParameter("rememberMe")
                        .rememberMeCookieName("rememberMe")
                );

        return http.build();
    }
    @Bean
    public MyUserDetailService userDetailsService(UserRepository userRepository) {
        return new MyUserDetailService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}