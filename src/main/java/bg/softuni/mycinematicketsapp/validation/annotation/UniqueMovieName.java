package bg.softuni.mycinematicketsapp.validation.annotation;

import bg.softuni.mycinematicketsapp.validation.UniqueMovieNameValidator;
import bg.softuni.mycinematicketsapp.validation.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueMovieNameValidator.class)
public @interface UniqueMovieName {

    String message() default "invalid Username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
