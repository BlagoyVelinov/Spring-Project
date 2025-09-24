package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;
import bg.softuni.mycinematicketsapp.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void testRegister_Success() throws Exception {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setUsername("newUser123");
        registerDto.setEmail("test@example.com");
        registerDto.setName("Test User");
        registerDto.setBirthdate(LocalDate.of(1995, 5, 15));
        registerDto.setPassword("password123");
        registerDto.setConfirmPassword("password123");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constant.SUCCESS_SIGNUP_USER));
    }

    @Test
    void testRegister_InvalidInput() throws Exception {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setUsername("");
        dto.setEmail("not-an-email");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void testActivate_Success() throws Exception {
        doNothing().when(userService).activateUserByToken("valid-token");

        mockMvc.perform(get("/api/users/activate")
                        .param("token", "valid-token"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "http://localhost:8082/users/login"));
    }

    @Test
    void testActivate_InvalidToken() throws Exception {
        doThrow(new IllegalArgumentException("Invalid activation token"))
                .when(userService).activateUserByToken("invalid-token");

        mockMvc.perform(get("/api/users/activate")
                        .param("token", "invalid-token"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid activation token"));
    }

    @Test
    void testGetSessionUser_NotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/users/session"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetSessionUser_Authenticated() throws Exception {
        mockMvc.perform(get("/api/users/session")
                        .with(user("sessionUser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isAuthenticated").value(true))
                .andExpect(jsonPath("$.username").value("sessionUser"));
    }
}
