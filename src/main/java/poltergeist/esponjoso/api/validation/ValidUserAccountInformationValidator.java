package poltergeist.esponjoso.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import poltergeist.esponjoso.api.entities.User;
import poltergeist.esponjoso.api.enums.AccountStatusEnum;

public class ValidUserAccountInformationValidator 
implements ConstraintValidator<ValidUserAccountInformation,User.UserAccountInformation>
{
    @Override
    public boolean isValid(User.UserAccountInformation userAccountInformation,
                            ConstraintValidatorContext constraintValidatorContext)
    {
        if(userAccountInformation == null) return true;

        boolean valid = true;
        constraintValidatorContext.disableDefaultConstraintViolation();

        var accountStatus = userAccountInformation.getAccountStatus();
        var suspendedAt = userAccountInformation.getSuspendedAt();

        if(AccountStatusEnum.ACTIVE.equals(accountStatus))
        {
            if(suspendedAt != null)
            {
                addFieldViolation(constraintValidatorContext,
                                "suspendedAt",
                                "{user.userAccountInformation.suspendedAt.invalid.active}");

                valid = false;
            }
        }

        if(AccountStatusEnum.SUSPENDED.equals(accountStatus))
        {
            if(suspendedAt == null)
            {
                addFieldViolation(constraintValidatorContext, 
                                "suspendedAt",
                                "{user.userAccountInformation.suspendedAt.invalid.suspended}");
                valid = false;
            }
        }

        return valid;
    }

    private void addFieldViolation(ConstraintValidatorContext constraintValidatorContext,
                                    String field,
                                    String message)
    {
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
            .addPropertyNode(field)
            .addConstraintViolation();
    }
}
