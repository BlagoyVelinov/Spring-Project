package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.ExceptionMessages;
import bg.softuni.mycinematicketsapp.models.dtos.AddOfferDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.OfferViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Offer;
import bg.softuni.mycinematicketsapp.models.enums.OfferType;
import bg.softuni.mycinematicketsapp.repository.OfferRepository;
import bg.softuni.mycinematicketsapp.services.OfferService;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Offer getOfferById(long offerId) {
        return this.offerRepository.findById(offerId)
                .orElseThrow(() -> new ObjectNotFoundException(ExceptionMessages.OFFER_NOT_FOUND));
    }

    @Override
    public void createOffer(AddOfferDto createOffer) {
        Offer offer = this.mapOfferDtoToOffer(createOffer);
        this.offerRepository.save(offer);
    }

    @Override
    public OfferViewDto getOfferViewById(long offerId) {
        Offer offer = this.getOfferById(offerId);
        return this.mapOfferToOfferDto(offer);
    }

    @Override
    public List<Offer> getAllOffers() {
        return this.offerRepository.findAll();
    }

    @Override
    public List<OfferViewDto> getAllCinemaOffers() {
        return this.getAllOffers()
                .stream()
                .filter(offer -> offer.getOfferCategory().equals(OfferType.CINEMA_OFFERS))
                .map(this::mapOfferToOfferDto)
                .toList();
    }

    @Override
    public List<OfferViewDto> getAllSchoolsOffers() {
        return this.getAllOffers()
                .stream()
                .filter(offer -> offer.getOfferCategory().equals(OfferType.FOR_THE_SCHOOLS))
                .map(this::mapOfferToOfferDto)
                .toList();
    }

    @Override
    public List<OfferViewDto> getAllBusinessOffers() {
        return this.getAllOffers()
                .stream()
                .filter(offer -> offer.getOfferCategory().equals(OfferType.FOR_THE_BUSINESS))
                .map(this::mapOfferToOfferDto)
                .toList();
    }

    @Override
    public void deleteOffer(long offerId) {
        this.offerRepository.deleteById(offerId);
    }

    @Override
    public OfferViewDto updateOffer(OfferViewDto offerDto, long id) {
        Offer offer = this.getOfferById(id);

        offer.setTitle(offerDto.getTitle())
                .setImageUrl(offerDto.getImageUrl())
                .setDescription(offerDto.getDescription());

        this.offerRepository.save(offer);
        return offerDto;
    }

    private Offer mapOfferDtoToOffer(AddOfferDto createOffer) {
        return this.modelMapper.map(createOffer, Offer.class);
    }

    private OfferViewDto mapOfferToOfferDto(Offer offer) {
        return this.modelMapper.map(offer, OfferViewDto.class);
    }
}
