package bg.softuni.mycinematicketsapp.config;

import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.services.session.MyUserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Value("${spring.cinema_tickets.remember.me.key}")
    private String rememberMeKey;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOriginPatterns(List.of("http://localhost:*"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.addExposedHeader("Authorization");
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthFilter jwtFilter,
                                                   DaoAuthenticationProvider daoAuthProvider)
            throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/",
                                "/api/program",
                                "/api/offers",
                                "/api/offers/offer/*",
                                "/api/users/login",
                                "/api/users/register",
                                "/api/users/activate",
                                "/api/contact"
                        ).permitAll()
                                .requestMatchers(
                                        "/api/users/all-users",
                                        "/api/offers/add-offer",
                                        "/api/offers/edit-offer/*",
                                        "/api/offers/delete-offer/*"
                                ).hasRole(UserRoleEnum.ADMINISTRATOR.name())
                        .requestMatchers(
                                "/api/users/**",
                                "/api/users/logout",
                                "/api/order/**",
                                "/api/order",
                                "/api/tickets/**"
                                ).authenticated()
                        .anyRequest().authenticated())
                .authenticationProvider(daoAuthProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider(MyUserDetailService userDetailsService,
                                                     PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
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