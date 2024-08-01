package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.AddOfferDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.OfferViewDto;
import bg.softuni.mycinematicketsapp.services.OfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @ModelAttribute("createOffer")
    public AddOfferDto initOffer() {
        return new AddOfferDto();
    }

    @GetMapping
    public String offers(Model model) {
        List<OfferViewDto> cinemaOffers = this.offerService.getAllCinemaOffers();
        model.addAttribute("cinemaOffers", cinemaOffers);
        List<OfferViewDto> schoolOffers = this.offerService.getAllSchoolsOffers();
        model.addAttribute("schoolOffers", schoolOffers);
        List<OfferViewDto> businessOffers = this.offerService.getAllBusinessOffers();
        model.addAttribute("businessOffers", businessOffers);
        return "offers";
    }

    @GetMapping("/add-offer")
    public String getAddOffer() {
        return "add-offer";
    }

    @PostMapping("/add-offer")
    public String postAddOffer(@Valid AddOfferDto createOffer,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute(Constant.OFFER_ATTRIBUTE_NAME, createOffer)
                    .addFlashAttribute(Constant.OFFER_BINDING_RESULT_NAME, bindingResult);
            return Constant.REDIRECT_ADD_OFFER;
        }

        this.offerService.createOffer(createOffer);
        return Constant.REDIRECT_OFFERS;
    }


    @GetMapping("/offer/{id}")
    public String getOffer(@PathVariable("id") long id, Model model) {
        OfferViewDto offerView = this.offerService.getOfferViewById(id);
        model.addAttribute("offerView", offerView);
        return "offer";
    }

    @DeleteMapping("/delete-offer/{id}")
    public String deleteOffer(@PathVariable("id") long id) {
        this.offerService.deleteOffer(id);
        return Constant.REDIRECT_OFFERS;
    }
}
