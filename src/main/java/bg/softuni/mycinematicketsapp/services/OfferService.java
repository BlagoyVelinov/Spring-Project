package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.AddOfferDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.OfferViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Offer;

import java.util.List;

public interface OfferService {
    Offer getOfferById(long offerId);
    void createOffer(AddOfferDto createOffer);

    OfferViewDto getOfferViewById(long id);

    List<Offer> getAllOffers();
    List<OfferViewDto> getAllCinemaOffers();
    List<OfferViewDto> getAllSchoolsOffers();
    List<OfferViewDto> getAllBusinessOffers();

    void deleteOffer(long offerId);

    OfferViewDto updateOffer(OfferViewDto offerDto, long id);
}
