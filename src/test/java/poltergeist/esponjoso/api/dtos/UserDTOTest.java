package poltergeist.esponjoso.api.dtos;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import poltergeist.esponjoso.api.testdata.builder.UserDTOTestDataBuilder;


@SpringBootTest
public class UserDTOTest
{
    @Autowired
    Validator validator;

    @Test
    void should_beValid_withDefaultBuilder()
    {
        UserDTO dto = UserDTOTestDataBuilder.aValidUser();

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assert violations.isEmpty() : "Violations detected: " + violations;
    }

    @Test
    void should_fail_whenUserIdIsNull()
    {
        UserDTO dto = new UserDTOTestDataBuilder()
                .withNullUserId()
                .build();

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);

        boolean containsUserIdViolation = violations.stream()
                                        .anyMatch(v -> v.getPropertyPath().toString().equals("userId"));

        assert containsUserIdViolation : "Expected violation on 'userId' but got: " + violations;
    }

    @Test
    void should_fail_whenCoinsNegative()
    {
        UserDTO dto = new UserDTOTestDataBuilder()
                .withNegativeCoins()
                .build();

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);

        boolean containsCoinsViolation = violations.stream()
                                            .anyMatch(v -> v.getPropertyPath().toString().equals("coins"));

        assert containsCoinsViolation : "Expected violation on 'coins' but got: " + violations;
    }

    //TODO CREATE ALL VALIDATION TESTS HERE


    /**
     * HELP: You can use this method to print violations
     * @param violations
     * @return
     */
    @SuppressWarnings({ "unused" })
    private static String formatViolations(Set<? extends jakarta.validation.ConstraintViolation<?>> violations)
    {
        if (violations == null || violations.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[\n");
        for (var v : violations) {
            sb.append("  ")
            .append(v.getPropertyPath())       // ruta: p. ej. "coins"
            .append(" -> ").append(v.getMessage()) // mensaje interpolado
            .append(" | invalidValue=").append(v.getInvalidValue()) // valor inválido
            .append(" | annotation=").append(v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()) // p. ej. PositiveOrZero
            .append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

}
