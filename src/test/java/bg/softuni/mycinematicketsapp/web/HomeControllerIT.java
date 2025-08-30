package bg.softuni.mycinematicketsapp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetHomePage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(view().name("index"));
    }
    @Test
    void testAboutUs() throws Exception {
        this.mockMvc.perform(get("/about-us")).andDo(print())
                .andExpect(view().name("about-us"));
    }
    @Test
    void testContactUs() throws Exception {
        this.mockMvc.perform(get("/contact-us")).andDo(print())
                .andExpect(view().name("contact-us"));
    }
    @Test
    void testFourDx() throws Exception {
        this.mockMvc.perform(get("/4-dx")).andDo(print())
                .andExpect(view().name("4-dx"));
    }
    @Test
    void testImax() throws Exception {
        this.mockMvc.perform(get("/imax")).andDo(print())
                .andExpect(view().name("imax"));
    }
}
