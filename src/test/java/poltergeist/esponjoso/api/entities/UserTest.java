package poltergeist.esponjoso.api.entities;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import poltergeist.esponjoso.api.testdata.builder.UserTestDataBuilder;

@SpringJUnitConfig(classes = UserTest.Config.class)
public class UserTest
{
    @Configuration
    static class Config
    {
        @Bean
        Validator validator()
        {
            return new LocalValidatorFactoryBean();
        }
    }

    @Autowired
    Validator validator;

    @Test
    void should_beValid_withDefaultBuilder()
    {
        User dto = UserTestDataBuilder.aValidUser();

        Set<ConstraintViolation<User>> violations = validator.validate(dto);
        assert violations.isEmpty() : "Violations detected: " + violations;
    }

    @Test
    void should_fail_whenUserIdIsNull()
    {
        User dto = new UserTestDataBuilder()
                .withNullUserId()
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(dto);

        boolean containsUserIdViolation = violations.stream()
                                        .anyMatch(v -> v.getPropertyPath().toString().equals("userId"));

        assert containsUserIdViolation : "Expected violation on 'userId' but got: " + violations;
    }

    @Test
    void should_fail_whenCoinsNegative()
    {
        User dto = new UserTestDataBuilder()
                .withNegativeCoins()
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(dto);

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
