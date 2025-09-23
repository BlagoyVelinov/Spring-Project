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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

    private OfferServiceImpl offerService;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Offer> offerCaptor;

    @BeforeEach
    void setUp() {
        this.offerService = new OfferServiceImpl(
                offerRepository,
                modelMapper
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
        Offer offer = getOffer(ConstantTest.TEST_OFFER_TITLE, OfferType.CINEMA_OFFERS, 1L);

        when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));

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
        when(offerRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> offerService.getOfferById(100L));
    }

    @Test
    void testGetOfferViewById() {
        Offer offer = getOffer(ConstantTest.TEST_OFFER_TITLE, OfferType.CINEMA_OFFERS, 1L);

        when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));

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
        Offer offer1 = getOffer(ConstantTest.TEST_OFFER_TITLE, OfferType.CINEMA_OFFERS, 1L);
        Offer offer2 = getOffer("Test title 2", OfferType.FOR_THE_BUSINESS, 2L);

        List<Offer> offers = List.of(offer1, offer2);
        when(offerRepository.findAll()).thenReturn(offers);

        List<Offer> foundedOffers = offerService.getAllOffers();

        Assertions.assertNotNull(foundedOffers);
        assertEquals(2, foundedOffers.size());
        assertEquals(offer1.getTitle(), foundedOffers.get(0).getTitle());
        assertEquals(offer2.getTitle(), foundedOffers.get(1).getTitle());

        Mockito.verify(offerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetAllCinemaOffers() {
        Offer offer1 = getOffer(ConstantTest.TEST_OFFER_TITLE, OfferType.CINEMA_OFFERS, 1L);
        Offer offer2 = getOffer("Test title 2", OfferType.CINEMA_OFFERS, 2L);
        Offer offer3 = getOffer("Test title 3", OfferType.FOR_THE_SCHOOLS, 3L);

        List<Offer> offers = List.of(offer1, offer2, offer3);
        when(offerRepository.findAll()).thenReturn(offers);

        Mockito.when(modelMapper.map(offer1, OfferViewDto.class)).thenReturn(new OfferViewDto());
        Mockito.when(modelMapper.map(offer2, OfferViewDto.class)).thenReturn(new OfferViewDto());

        List<OfferViewDto> cinemaOffers = offerService.getAllCinemaOffers();

        Assertions.assertNotNull(cinemaOffers);
        Assertions.assertEquals(2, cinemaOffers.size());

        Mockito.verify(offerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetAllSchoolsOffers() {
        Offer offer1 = getOffer(ConstantTest.TEST_OFFER_TITLE, OfferType.FOR_THE_SCHOOLS, 1L);
        Offer offer2 = getOffer("Test title 2", OfferType.CINEMA_OFFERS, 2L);
        Offer offer3 = getOffer("Test title 3", OfferType.FOR_THE_SCHOOLS, 3L);

        List<Offer> offers = List.of(offer1, offer2, offer3);
        when(offerRepository.findAll()).thenReturn(offers);

        Mockito.when(modelMapper.map(offer1, OfferViewDto.class)).thenReturn(new OfferViewDto());
        Mockito.when(modelMapper.map(offer3, OfferViewDto.class)).thenReturn(new OfferViewDto());

        List<OfferViewDto> cinemaOffers = offerService.getAllSchoolsOffers();

        Assertions.assertNotNull(cinemaOffers);
        Assertions.assertEquals(2, cinemaOffers.size());

        Mockito.verify(offerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetAllBusinessOffers() {
        Offer offer1 = getOffer(ConstantTest.TEST_OFFER_TITLE, OfferType.FOR_THE_SCHOOLS, 1L);
        Offer offer2 = getOffer("Test title 2", OfferType.FOR_THE_BUSINESS, 2L);
        Offer offer3 = getOffer("Test title 3", OfferType.FOR_THE_BUSINESS, 3L);

        List<Offer> offers = List.of(offer1, offer2, offer3);
        when(offerRepository.findAll()).thenReturn(offers);

        Mockito.when(modelMapper.map(offer2, OfferViewDto.class)).thenReturn(new OfferViewDto());
        Mockito.when(modelMapper.map(offer3, OfferViewDto.class)).thenReturn(new OfferViewDto());

        List<OfferViewDto> cinemaOffers = offerService.getAllBusinessOffers();

        Assertions.assertNotNull(cinemaOffers);
        Assertions.assertEquals(2, cinemaOffers.size());

        Mockito.verify(offerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testDeleteOffer() {
        long offerId = 10L;

        offerService.deleteOffer(offerId);

        verify(offerRepository, Mockito.times(1)).deleteById(offerId);
    }

    @Test
    void testUpdateOffer() {
        Offer offer = getOffer(ConstantTest.TEST_OFFER_TITLE, OfferType.FOR_THE_SCHOOLS, 1L);
        OfferViewDto viewDto = getOfferViewDto();

        when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));

        OfferViewDto result = offerService.updateOffer(viewDto, offer.getId());

        Assertions.assertNotNull(result);
        assertEquals(offer.getTitle(), result.getTitle());
        assertEquals(offer.getDescription(), result.getDescription());
        assertEquals(offer.getOfferCategory(), result.getOfferCategory());

        Mockito.verify(offerRepository, Mockito.times(1)).save(offer);
    }


    private static AddOfferDto getAddOfferDto() {
        return new AddOfferDto()
                .setTitle(ConstantTest.TEST_OFFER_TITLE)
                .setOfferCategory(OfferType.FOR_THE_BUSINESS)
                .setDescription(ConstantTest.TEST_OFFER_DESCRIPTION)
                .setImageUrl(ConstantTest.TEST_OFFER_IMAGE_URL);
    }

    private static Offer getOffer(String title, OfferType offerType, long offerId) {
        Offer offer = new Offer()
                .setTitle(title)
                .setOfferCategory(offerType)
                .setDescription(ConstantTest.TEST_OFFER_DESCRIPTION)
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
