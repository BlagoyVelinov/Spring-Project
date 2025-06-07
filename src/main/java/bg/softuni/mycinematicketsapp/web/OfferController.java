package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.AddOfferDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.OfferViewDto;
import bg.softuni.mycinematicketsapp.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<?> getOffers() {
        List<OfferViewDto> cinemaOffers = this.offerService.getAllCinemaOffers();
        List<OfferViewDto> schoolOffers = this.offerService.getAllSchoolsOffers();
        List<OfferViewDto> businessOffers = this.offerService.getAllBusinessOffers();

        return ResponseEntity.ok(Map.of(
                "cinemaOffers", cinemaOffers,
                "schoolOffers", schoolOffers,
                "businessOffers", businessOffers
        ));
    }

    @GetMapping("/offer/{id}")
    public ResponseEntity<?> getOffer(@PathVariable("id") long id) {
        OfferViewDto offerView = this.offerService.getOfferViewById(id);
        return ResponseEntity.ok(offerView);
    }

    @PostMapping("/add-offer")
    public ResponseEntity<?> addOffer(@Valid @RequestBody AddOfferDto createOffer,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("errors", bindingResult.getAllErrors()));
        }
        this.offerService.createOffer(createOffer);
        return ResponseEntity.status(201).body(Map.of("message", "Offer created successfully"));
    }

    @DeleteMapping("/delete-offer/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable("id") long id) {
        this.offerService.deleteOffer(id);
        return ResponseEntity.ok(Map.of("message", "Offer deleted successfully"));
    }
}
