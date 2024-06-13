package bg.softuni.mycinematicketsapp.validation.annotation;

import bg.softuni.mycinematicketsapp.validation.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {

    String message() default "invalid Username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
