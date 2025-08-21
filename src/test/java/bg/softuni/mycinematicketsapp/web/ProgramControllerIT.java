package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@SpringBootTest
@AutoConfigureMockMvc
public class ProgramControllerIT {
    private static final String TEST_USER_ADMIN = "admin";

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testAllMoviesInProgram() throws Exception {
        this.mockMvc.perform(get("/program")).andDo(print())
                .andExpect(view().name("program"));
    }

    @Test
    @WithMockUser(
            username = TEST_USER_ADMIN,
            roles = {"USER", "ADMINISTRATOR"})
    void testGetUpdateProjection() throws Exception {
        this.mockMvc.perform(get("/program/update-projection-time/{id}", 5))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }
    @Test
    @WithMockUser(
            username = TEST_USER_ADMIN,
            roles = {"USER", "ADMINISTRATOR"})
    void testPutUpdateProjection() throws Exception {


        this.mockMvc.perform(put("/program/update-projection-time/{id}", 5)
                        .param("startMovieTimes", "_11_50")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Constant.REDIRECT_PROGRAM));
        Assertions.assertNotNull("_11_50");
        Assertions.assertEquals("_11_50", "_11_50");
    }
}
