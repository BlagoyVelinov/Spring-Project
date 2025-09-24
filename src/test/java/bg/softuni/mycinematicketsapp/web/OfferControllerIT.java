package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import bg.softuni.mycinematicketsapp.models.dtos.AddOfferDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.OfferViewDto;
import bg.softuni.mycinematicketsapp.models.enums.OfferType;
import bg.softuni.mycinematicketsapp.services.OfferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OfferControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @Test
    void testGetOffers() throws Exception {
        OfferViewDto cinemaOffer = new OfferViewDto();
        cinemaOffer.setTitle(ConstantTest.TEST_OFFER_TITLE_1);

        OfferViewDto schoolOffer = new OfferViewDto();
        schoolOffer.setTitle(ConstantTest.TEST_OFFER_TITLE_2);

        OfferViewDto businessOffer = new OfferViewDto();
        businessOffer.setTitle(ConstantTest.TEST_OFFER_TITLE_3);

        when(offerService.getAllCinemaOffers()).thenReturn(List.of(cinemaOffer));
        when(offerService.getAllSchoolsOffers()).thenReturn(List.of(schoolOffer));
        when(offerService.getAllBusinessOffers()).thenReturn(List.of(businessOffer));

        mockMvc.perform(get("/api/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cinemaOffers[0].title").value(ConstantTest.TEST_OFFER_TITLE_1))
                .andExpect(jsonPath("$.schoolOffers[0].title").value(ConstantTest.TEST_OFFER_TITLE_2))
                .andExpect(jsonPath("$.businessOffers[0].title").value(ConstantTest.TEST_OFFER_TITLE_3));
    }

    @Test
    void testGetOfferById() throws Exception {
        OfferViewDto offerViewDto = new OfferViewDto();
        offerViewDto.setTitle(ConstantTest.TEST_OFFER_TITLE);

        when(offerService.getOfferViewById(1L)).thenReturn(offerViewDto);

        mockMvc.perform(get("/api/offers/offer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(ConstantTest.TEST_OFFER_TITLE));
    }

    @Test
    void testAddOffer_Success() throws Exception {
        AddOfferDto addOfferDto = new AddOfferDto();
        addOfferDto.setTitle(ConstantTest.TEST_OFFER_TITLE_1);
        addOfferDto.setDescription(ConstantTest.TEST_OFFER_DESCRIPTION);
        addOfferDto.setImageUrl(ConstantTest.TEST_OFFER_IMAGE_URL);
        addOfferDto.setOfferCategory(OfferType.FOR_THE_SCHOOLS);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/offers/add-offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addOfferDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constant.SUCCESS_CREATED_OFFER));

        verify(offerService, times(1)).createOffer(any(AddOfferDto.class));
    }

    @Test
    void testEditOffer() throws Exception {
        OfferViewDto offerViewDto = new OfferViewDto();
        offerViewDto.setTitle(ConstantTest.TEST_OFFER_TITLE_3);

        when(offerService.updateOffer(any(OfferViewDto.class), eq(1L))).thenReturn(offerViewDto);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/api/offers/edit-offer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerViewDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(ConstantTest.TEST_OFFER_TITLE_3));
    }

    @Test
    void testDeleteOffer() throws Exception {
        mockMvc.perform(delete("/api/offers/delete-offer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constant.SUCCESS_DELETED_OFFER));

        verify(offerService, times(1)).deleteOffer(1L);
    }
}
