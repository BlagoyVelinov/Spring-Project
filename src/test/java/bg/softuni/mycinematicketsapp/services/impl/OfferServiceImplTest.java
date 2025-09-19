package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import bg.softuni.mycinematicketsapp.models.dtos.AddOfferDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.OfferViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Offer;
import bg.softuni.mycinematicketsapp.models.enums.OfferType;
import bg.softuni.mycinematicketsapp.repository.OfferRepository;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

    private OfferServiceImpl offerService;

    @Mock
    private OfferRepository offerRepository;

    @Captor
    private ArgumentCaptor<Offer> offerCaptor;

    @BeforeEach
    void setUp() {
        this.offerService = new OfferServiceImpl(
                offerRepository,
                new ModelMapper()
        );
    }

    @Test
    void testCreateOffer() {
        AddOfferDto offerDto = getAddOfferDto();
        offerService.createOffer(offerDto);

        verify(offerRepository).save(offerCaptor.capture());

        Offer offer = offerCaptor.getValue();

        Assertions.assertNotNull(offer);
        assertEquals(offerDto.getTitle(), offer.getTitle());
        assertEquals(offerDto.getOfferCategory(), offer.getOfferCategory());
        assertEquals(offerDto.getDescription(), offer.getDescription());
        assertEquals(offerDto.getImageUrl(), offer.getImageUrl());

        Mockito.verify(offerRepository, Mockito.times(1)).save(offer);
    }

    @Test
    void testGetOfferById_Success() {
        Offer offer = getOffer(ConstantTest.TEST_OFFER_TITLE, ConstantTest.TEST_OFFER_DESCRIPTION, 1L);

        Mockito.when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));

        Offer result = offerService.getOfferById(offer.getId());

        Assertions.assertNotNull(result);
        assertEquals(offer.getTitle(), result.getTitle());
        assertEquals(offer.getOfferCategory(), result.getOfferCategory());
        assertEquals(offer.getDescription(), result.getDescription());
        assertEquals(offer.getImageUrl(), result.getImageUrl());

        Mockito.verify(offerRepository, Mockito.times(1)).findById(offer.getId());
    }

    @Test
    void testGetOfferById_NotFound() {
        Mockito.when(offerRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> offerService.getOfferById(100L));
    }

    @Test
    void testGetOfferViewById() {
        Offer offer = getOffer(ConstantTest.TEST_OFFER_TITLE, ConstantTest.TEST_OFFER_DESCRIPTION, 1L);

        Mockito.when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));

        OfferViewDto result = offerService.getOfferViewById(offer.getId());

        Assertions.assertNotNull(result);
        assertEquals(offer.getTitle(), result.getTitle());
        assertEquals(offer.getOfferCategory(), result.getOfferCategory());
        assertEquals(offer.getDescription(), result.getDescription());
        assertEquals(offer.getImageUrl(), result.getImageUrl());

        Mockito.verify(offerRepository, Mockito.times(1)).findById(offer.getId());
    }

    @Test
    void testGetAllOffers() {
        Offer offer1 = getOffer(ConstantTest.TEST_OFFER_TITLE, ConstantTest.TEST_OFFER_DESCRIPTION, 1L);
        Offer offer2 = getOffer("Test title 2", "Test description 2", 2L);

        List<Offer> offers = List.of(offer1, offer2);
        Mockito.when(offerRepository.findAll()).thenReturn(offers);

        List<Offer> foundedOffers = offerService.getAllOffers();

        Assertions.assertNotNull(foundedOffers);
        assertEquals(2, foundedOffers.size());
        assertEquals(offer1.getTitle(), foundedOffers.get(0).getTitle());
        assertEquals(offer2.getTitle(), foundedOffers.get(1).getTitle());

        Mockito.verify(offerRepository, Mockito.times(1)).findAll();
    }

    private static AddOfferDto getAddOfferDto() {
        return new AddOfferDto()
                .setTitle(ConstantTest.TEST_OFFER_TITLE)
                .setOfferCategory(OfferType.FOR_THE_BUSINESS)
                .setDescription(ConstantTest.TEST_OFFER_DESCRIPTION)
                .setImageUrl(ConstantTest.TEST_OFFER_IMAGE_URL);
    }

    private static Offer getOffer(String title, String description, long offerId) {
        Offer offer = new Offer()
                .setTitle(title)
                .setOfferCategory(OfferType.CINEMA_OFFERS)
                .setDescription(description)
                .setImageUrl(ConstantTest.TEST_OFFER_IMAGE_URL);

        offer.setId(offerId);

        return offer;
    }

    private static OfferViewDto getOfferViewDto() {
        return new OfferViewDto()
                .setId(10L)
                .setTitle(ConstantTest.TEST_OFFER_TITLE)
                .setOfferCategory(OfferType.FOR_THE_SCHOOLS)
                .setDescription(ConstantTest.TEST_OFFER_DESCRIPTION)
                .setImageUrl(ConstantTest.TEST_OFFER_IMAGE_URL);
    }
}
