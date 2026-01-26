package poltergeist.esponjoso.api.dtos;

import poltergeist.esponjoso.api.enums.AccountStatusEnum;
import poltergeist.esponjoso.api.enums.AccountTypeEnum;
import poltergeist.esponjoso.api.validation.ValidEmail;
import poltergeist.esponjoso.api.validation.ValidPassword;
import poltergeist.esponjoso.api.validation.ValidUserAccountInformation;
import poltergeist.esponjoso.api.validation.ValidUserStatistics;
import poltergeist.esponjoso.api.validation.ValidUsername;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO
{
    @NotBlank(message = "{user.id.notBlank}")
    private String userId;

    @ValidUsername
    private String username;

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;

    @NotNull(message = "{user.coins.notNull}")
    @PositiveOrZero(message = "{user.coins.balance}")
    private Double coins;

    @Valid
    @NotNull(message = "{user.userAccountInformation.notNull}")
    private UserAccountInformation userAccountInformation;
    
    @Valid
    @NotNull(message = "{user.userStatistics.notNull}")
    private UserStatistics userStatistics;

    /**
     * UserDTO.AccountInformation
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ValidUserAccountInformation
    public static class UserAccountInformation
    {
        @NotNull(message = "{user.accountInformation.accountType.notNull}")
        AccountTypeEnum accountType;
        @NotNull(message = "{user.accountInformation.accountStatus.notNull}")
        AccountStatusEnum accountStatus;
        @NotNull(message = "{user.accountInformation.isOnline.notNull}")
        Boolean isOnline;
        @NotNull(message = "{user.accountInformation.createdAt.notNull}")
        @PastOrPresent(message = "{user.accountInformation.createdAt.invalid}")
        LocalDateTime createdAt;
        LocalDateTime suspendedAt;
    }

    /**
     * UserDTO.UserStatistics
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ValidUserStatistics
    public static class UserStatistics
    {
        @PositiveOrZero(message = "{user.userStatistics.connectedTimes.invalid}")
        double connectedTimes;
        @PositiveOrZero(message = "{user.userStatistics.generatedApiKeys.invalid}")
        double generatedApiKeys;
        @PositiveOrZero(message = "{user.userStatistics.apiCallsNumber.invalid}")
        double apiCallsNumber;
    }
}
