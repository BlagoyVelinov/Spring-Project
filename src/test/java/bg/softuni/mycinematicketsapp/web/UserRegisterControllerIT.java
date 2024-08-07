package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegisterUserGet() throws Exception {
        this.mockMvc.perform(get("/users/register"))
                .andDo(print())
                .andExpect(view().name("register"));
    }

    @Test
    void testCheckEmailGet() throws Exception {
        this.mockMvc.perform(get("/users/check-email"))
                .andDo(print())
                .andExpect(view().name("check-email"));
    }

    @Test
    void testRegisterUserPost() throws Exception {
        this.mockMvc.perform(post("/users/register")
                        .param("username", ConstantTest.TEST_USERNAME)
                        .param("email", ConstantTest.USER_EMAIL)
                        .param("name", ConstantTest.TEST_NAME)
                        .param("birthdate", ConstantTest.TEST_USER_BIRTHDATE)
                        .param("password", ConstantTest.TEST_PASSWORD)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(Constant.REDIRECT_AFTER_REGISTER));
    }

    @Test
    void testIncorrectRegisterUserPost() throws Exception {
        this.mockMvc.perform(post("/users/register")
                        .param("username", ConstantTest.TEST_USERNAME)
                        .param("email", ConstantTest.USER_EMAIL)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(Constant.REDIRECT_REGISTER));
    }
}
