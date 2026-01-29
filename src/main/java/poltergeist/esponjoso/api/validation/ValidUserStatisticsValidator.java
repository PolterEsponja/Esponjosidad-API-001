package poltergeist.esponjoso.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import poltergeist.esponjoso.api.entities.User;

public class ValidUserStatisticsValidator implements ConstraintValidator<ValidUserStatistics, User.UserStatistics>
{
    @Override
    public boolean isValid(User.UserStatistics userStatistics, ConstraintValidatorContext constraintValidatorContext)
    {
        if(userStatistics == null) return true;

        boolean valid = true;
        constraintValidatorContext.disableDefaultConstraintViolation();

        var generatedApiKeys = userStatistics.getGeneratedApiKeys();
        var apiCallsNumber = userStatistics.getApiCallsNumber();

        if(Double.compare(userStatistics.getConnectedTimes(), 0d) == 0)
        {
            if(generatedApiKeys > 0d)
            {
                addFieldViolation(constraintValidatorContext, "generatedApiKeys", "{user.userStatistics.generatedApiKeys.invalid}");
                valid = false;
            }
            if(apiCallsNumber > 0d)
            {
                addFieldViolation(constraintValidatorContext, "apiCallsNumber", "{user.userStatistics.apiCallsNumber.invalid}");
                valid = false;
            }
        }

        if(Double.compare(generatedApiKeys, 0d) == 0)
        {
            if(apiCallsNumber > 0d)
            {
                addFieldViolation(constraintValidatorContext, "apiCallsNumber", "{user.userStatistics.apiCallsNumber.invalid}");
                valid = false;
            }
        }

        return valid;
    }

    private void addFieldViolation(ConstraintValidatorContext constraintValidatorContext, String field, String message)
    {
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
            .addPropertyNode(field)
            .addConstraintViolation();
    }
}
