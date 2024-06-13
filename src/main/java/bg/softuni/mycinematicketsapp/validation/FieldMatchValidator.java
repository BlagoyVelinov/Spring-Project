package bg.softuni.mycinematicketsapp.validation;

import bg.softuni.mycinematicketsapp.validation.annotation.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String first;
    private String second;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value); // JavaBeen Object
        Object firstValue = beanWrapper.getPropertyValue(this.first);
        Object secondValue = beanWrapper.getPropertyValue(this.second);

        boolean isValid;
        if (firstValue == null) {
            isValid = secondValue == null;
        }else {
            isValid = firstValue.equals(secondValue);
        }

        if (!isValid) {
            context.buildConstraintViolationWithTemplate(this.message)
                    .addPropertyNode(this.second)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return isValid;
    }
}
