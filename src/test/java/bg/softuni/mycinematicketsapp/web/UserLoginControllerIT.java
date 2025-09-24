package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.config.SecurityUserDetails;
import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import bg.softuni.mycinematicketsapp.models.dtos.helpers.LoginRequest;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserLoginControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    private SecurityUserDetails testUser;

    @BeforeEach
    void setUp() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2L);
        userEntity.setUsername(ConstantTest.TEST_USERNAME);
        userEntity.setPassword(ConstantTest.TEST_PASSWORD);

        testUser = new SecurityUserDetails(userEntity);
    }

    @Test
    void testLogin_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest(ConstantTest.TEST_USERNAME, ConstantTest.TEST_PASSWORD);

        Authentication auth = new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

        when(jwtService.generateToken(testUser)).thenReturn(ConstantTest.TEST_TOKEN);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.username").value(ConstantTest.TEST_USERNAME))
                .andExpect(jsonPath("$.accessToken").value(ConstantTest.TEST_TOKEN))
                .andExpect(jsonPath("$.admin").value(false));
    }

    @Test
    void testLogin_BadCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest(ConstantTest.TEST_USERNAME, ConstantTest.TEST_PASSWORD);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.badCredentials").value(true));
    }

    @Test
    void testLogout_Success() throws Exception {
        mockMvc.perform(post("/api/users/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constant.SUCCESS_LOGOUT_USER));
    }
}