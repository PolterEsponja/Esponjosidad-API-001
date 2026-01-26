package poltergeist.esponjoso.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT, ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "{user.username.notBlank}")
@Size(min = 5, max = 20, message = "{user.username.size}")
@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "{user.username.pattern}")
public @interface ValidUsername
{
    String message() default "{user.username.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
