package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import bg.softuni.mycinematicketsapp.models.dtos.AddOfferDto;
import bg.softuni.mycinematicketsapp.models.entities.Offer;
import bg.softuni.mycinematicketsapp.models.enums.OfferType;
import bg.softuni.mycinematicketsapp.repository.OfferRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

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
        Offer offer = getOffer();

        Mockito.when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));

        Offer result = offerService.getOfferById(offer.getId());

        Assertions.assertNotNull(result);
        assertEquals(offer.getTitle(), result.getTitle());
        assertEquals(offer.getOfferCategory(), result.getOfferCategory());
        assertEquals(offer.getDescription(), result.getDescription());
        assertEquals(offer.getImageUrl(), result.getImageUrl());

        Mockito.verify(offerRepository, Mockito.times(1)).findById(offer.getId());
    }

    private static AddOfferDto getAddOfferDto() {
        return new AddOfferDto()
                .setTitle(ConstantTest.TEST_OFFER_TITLE)
                .setOfferCategory(OfferType.CINEMA_OFFERS)
                .setDescription(ConstantTest.TEST_OFFER_DESCRIPTION)
                .setImageUrl(ConstantTest.TEST_OFFER_IMAGE_URL);
    }

    private static Offer getOffer() {
        Offer offer = new Offer()
                .setTitle(ConstantTest.TEST_OFFER_TITLE)
                .setOfferCategory(OfferType.CINEMA_OFFERS)
                .setDescription(ConstantTest.TEST_OFFER_DESCRIPTION)
                .setImageUrl(ConstantTest.TEST_OFFER_IMAGE_URL);

        offer.setId(1L);

        return offer;
    }
}
