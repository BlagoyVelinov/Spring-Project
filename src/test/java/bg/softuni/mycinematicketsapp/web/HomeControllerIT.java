package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/home/about-us връща ABOUT_US")
    void testGetAboutUsInfo() throws Exception {
        mockMvc.perform(get("/api/home/about-us"))
                .andExpect(status().isOk())
                .andExpect(content().string(Constant.ABOUT_US));
    }

    @Test
    @DisplayName("GET /api/home/contact-us връща CONTACT_US")
    void testGetContactUsInfo() throws Exception {
        mockMvc.perform(get("/api/home/contact-us"))
                .andExpect(status().isOk())
                .andExpect(content().string(Constant.CONTACT_US));
    }

    @Test
    @DisplayName("GET /api/home/4-dx връща FOUR_DX")
    void testGet4DxInfo() throws Exception {
        mockMvc.perform(get("/api/home/4-dx"))
                .andExpect(status().isOk())
                .andExpect(content().string(Constant.FOUR_DX));
    }

    @Test
    @DisplayName("GET /api/home/imax връща IMAX")
    void testGetImaxInfo() throws Exception {
        mockMvc.perform(get("/api/home/imax"))
                .andExpect(status().isOk())
                .andExpect(content().string(Constant.IMAX));
    }
}
