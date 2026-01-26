package poltergeist.esponjoso.api.validation;

import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidUserStatisticsValidator.class)
public @interface ValidUserStatistics
{
    String message() default "Invalid userStatistics values.";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
