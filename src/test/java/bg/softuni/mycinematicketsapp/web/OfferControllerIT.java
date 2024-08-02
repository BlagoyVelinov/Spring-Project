package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.entities.Offer;
import bg.softuni.mycinematicketsapp.models.enums.OfferType;
import bg.softuni.mycinematicketsapp.repository.OfferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerIT {
    private static final String TEST_USER_ADMIN = "admin";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;

    @AfterEach
    public void tearDown() {
        this.offerRepository.deleteAll();
    }
    @Test
    @WithMockUser(
            username = TEST_USER_ADMIN,
            roles = {"USER", "ADMINISTRATOR"})
    void testPostAddOffer() throws Exception {

        this.mockMvc.perform(post("/offers/add-offer")
                .param("title", "testTitle")
                .param("description", "testDescription")
                .param("imageUrl", "/images/rent-a-hall.jpg")
                .param("offerCategory", "CINEMA_OFFERS")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Constant.REDIRECT_OFFERS));
        Optional<Offer> testOfferOpt = this.offerRepository.findByTitle("testTitle");

        Assertions.assertTrue(testOfferOpt.isPresent());

        Offer offer = testOfferOpt.get();
        Assertions.assertEquals("testDescription", offer.getDescription());
        Assertions.assertEquals("/images/rent-a-hall.jpg", offer.getImageUrl());
        Assertions.assertEquals("CINEMA_OFFERS", offer.getOfferCategory().name());
    }

    @Test
    void testGetAddOffer() throws Exception {
        this.mockMvc.perform(get("/offers/add-offer")).andDo(print())
                .andExpect(view().name("add-offer"));

    }

    @Test
    void testGetAllOffers() throws Exception {
        this.mockMvc.perform(get("/offers")).andDo(print())
                .andExpect(view().name("offers"));

    }

    @Test
    void testGetOfferById() throws Exception {
        Offer offer = this.createOffer();
        this.mockMvc.perform(get("/offers/offer/{id}", offer.getId()))
                .andDo(print())
                .andExpect(view().name("offer"));
    }

    @Test
    @WithMockUser(
            username = TEST_USER_ADMIN,
            roles = {"USER", "ADMINISTRATOR"})
    void testDeleteOfferById() throws Exception {
        Offer offer = this.createOffer();
        this.mockMvc.perform(delete("/offers/delete-offer/{id}", offer.getId())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Constant.REDIRECT_OFFERS));

        Optional<Offer> testOfferOpt = this.offerRepository.findById(offer.getId());

        Assertions.assertTrue(testOfferOpt.isEmpty());

    }

    private Offer createOffer() {

        Offer offer = new Offer();
        offer.setId(1);
        offer.setTitle("NewOffer")
                .setDescription("Testing creating new Offer!")
                .setImageUrl("/images/rent-a-hall.jpg")
                .setOfferCategory(OfferType.CINEMA_OFFERS);
        return this.offerRepository.save(offer);
    }
}
