package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginControllerIT {
    private static final String TEST_USER_ADMIN = "admin";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginUser() throws Exception {
        this.mockMvc.perform(get("/users/login"))
                .andDo(print())
                .andExpect(view().name("login"));
    }

    @Test
    void testLoginUserError() throws Exception {
        this.mockMvc.perform(post("/users/login-error")
                .param("username", TEST_USER_ADMIN)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Constant.REDIRECT_LOGIN));
    }
}
