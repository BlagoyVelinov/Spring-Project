package bg.softuni.mycinematicketsapp.validation.annotation;

import bg.softuni.mycinematicketsapp.validation.UniqueEmailValidator;
import bg.softuni.mycinematicketsapp.validation.UniqueOfferValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueOfferValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueOfferName {
    String message() default "Offer already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
