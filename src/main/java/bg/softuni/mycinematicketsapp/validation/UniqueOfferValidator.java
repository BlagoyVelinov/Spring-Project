package bg.softuni.mycinematicketsapp.validation;

import bg.softuni.mycinematicketsapp.repository.OfferRepository;
import bg.softuni.mycinematicketsapp.validation.annotation.UniqueOfferName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueOfferValidator implements ConstraintValidator<UniqueOfferName, String> {

    private final OfferRepository offerRepository;

    public UniqueOfferValidator(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        return this.offerRepository.findByTitle(title).isEmpty();
    }
}
