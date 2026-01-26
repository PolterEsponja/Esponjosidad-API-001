package poltergeist.esponjoso.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "{user.email.notBlank}")
@Size(max = 100, message = "{user.email.size}")
@Email(message = "{user.email.pattern}")
public @interface ValidEmail
{
    String message() default "{user.email.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
