package poltergeist.esponjoso.api.dtos;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import poltergeist.esponjoso.api.enums.AccountStatusEnum;
import poltergeist.esponjoso.api.enums.AccountTypeEnum;
import poltergeist.esponjoso.api.validation.ValidEmail;
import poltergeist.esponjoso.api.validation.ValidUsername;

public record UserResponseDTO(
    @NotBlank(message = "{user.id.notBlank}")
    String userId,

    @ValidUsername
    String username,

    @ValidEmail
    String email,

    @NotNull(message = "{user.coins.notNull}")
    @PositiveOrZero(message = "{user.coins.balance}")
    Double coins,

    @Valid
    @NotNull(message = "{user.userAccountInformation.notNull}")
    UserAccountInformation userAccountInformation,
    
    @Valid
    @NotNull(message = "{user.userStatistics.notNull}")
    UserStatistics userStatistics
)
{
    public record UserAccountInformation(
        @NotNull(message = "{user.accountInformation.accountType.notNull}")
        AccountTypeEnum accountType,
        @NotNull(message = "{user.accountInformation.accountStatus.notNull}")
        AccountStatusEnum accountStatus,
        @NotNull(message = "{user.accountInformation.isOnline.notNull}")
        Boolean isOnline,
        @NotNull(message = "{user.accountInformation.createdAt.notNull}")
        @PastOrPresent(message = "{user.accountInformation.createdAt.invalid}")
        LocalDateTime createdAt,
        LocalDateTime suspendedAt
    ){}

    public record UserStatistics(
        @PositiveOrZero(message = "{user.userStatistics.connectedTimes.invalid}")
        double connectedTimes,
        @PositiveOrZero(message = "{user.userStatistics.generatedApiKeys.invalid}")
        double generatedApiKeys,
        @PositiveOrZero(message = "{user.userStatistics.apiCallsNumber.invalid}")
        double apiCallsNumber
    ){}
}
