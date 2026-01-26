package poltergeist.esponjoso.api.testdata.builder;

import java.time.LocalDateTime;
import java.util.UUID;

import poltergeist.esponjoso.api.dtos.UserDTO;
import poltergeist.esponjoso.api.dtos.UserDTO.UserAccountInformation;
import poltergeist.esponjoso.api.dtos.UserDTO.UserStatistics;
import poltergeist.esponjoso.api.enums.AccountStatusEnum;
import poltergeist.esponjoso.api.enums.AccountTypeEnum;

public class UserDTOTestDataBuilder
{
    private String userId;
    private String username;
    private String email;
    private String password;
    private Double coins;
    private UserAccountInformation userAccountInformation;
    private UserStatistics userStatistics;

    /**
     * OBJECT CONSTRUCTOR - PRINCIPAL CLASS - UserDTO
     */
    public UserDTOTestDataBuilder()
    {
        this.userId = UUID.randomUUID().toString();
        this.username = "PolterEsponja10";
        this.email = "polteresponja10@example.com";
        this.password = "Str0ngPass02983";
        this.coins = 10.0;
        this.userAccountInformation = new UserAccountInformationBuilder().build();
        this.userStatistics = new UserStatisticsBuilder().build();
    }

    /**
     * OK CASES
     */
    public UserDTOTestDataBuilder withUserId(String userId)
    {
        this.userId = userId;
        return this;
    }
    public UserDTOTestDataBuilder withUsername(String username)
    {
        this.username = username;
        return this;
    }
    public UserDTOTestDataBuilder withEmail(String email)
    {
        this.email = email;
        return this;
    }
    public UserDTOTestDataBuilder withPassword(String password)
    {
        this.password = password;
        return this;
    }

    public UserDTOTestDataBuilder withCoins(Double coins)
    {
        this.coins = coins;
        return this;
    }
    public UserDTOTestDataBuilder withUserAccountInformation(UserAccountInformation userAccountInformation)
    {
        this.userAccountInformation = userAccountInformation;
        return this;
    }
    public UserDTOTestDataBuilder withUserStatistics(UserStatistics userStatistics)
    {
        this.userStatistics = userStatistics;
        return this;
    }

    /**
     * ERROR CASES
     */
    public UserDTOTestDataBuilder withNullUserId()
    {
        this.userId = null;
        return this;
    }
    public UserDTOTestDataBuilder withNegativeCoins()
    {
        this.coins = -1.0;
        return this;
    }
    public UserDTOTestDataBuilder withNullAccountInformation()
    {
        this.userAccountInformation = null;
        return this;
    }
    public UserDTOTestDataBuilder withNullStatistics()
    {
        this.userStatistics = null;
        return this;
    }

    /**
     * BUILDER CONSTRUCTOR - PRINCIPAL CLASS - UserDTO
     * @return
     */
    public UserDTO build()
    {
        UserDTO dto = new UserDTO();
        dto.setUserId(this.userId);
        dto.setUsername(this.username);
        dto.setEmail(this.email);
        dto.setPassword(this.password);
        dto.setCoins(this.coins);
        dto.setUserAccountInformation(this.userAccountInformation);
        dto.setUserStatistics(this.userStatistics);

        return dto;
    }

    /**
     * OBJECT - INTERNAL CLASS OF UserDTO - UserAccountInformation
     */
    public static class UserAccountInformationBuilder
    {
        private AccountTypeEnum accountType;
        private AccountStatusEnum accountStatus;
        private Boolean isOnline;
        private LocalDateTime createdAt;
        private LocalDateTime suspendedAt;

        /**
         * OBJECT CONSTRUCTOR - INTERNAL CLASS OF UserDTO- UserAccountInformation
         */
        public UserAccountInformationBuilder()
        {
            this.accountType = AccountTypeEnum.BASIC;
            this.accountStatus = AccountStatusEnum.ACTIVE;
            this.isOnline = Boolean.FALSE;
            this.createdAt = LocalDateTime.now().minusDays(1);
            this.suspendedAt = null;
        }

        /**
         * OK CASES
         */
        public UserAccountInformationBuilder withAccountType(AccountTypeEnum accountType)
        {
            this.accountType = accountType;
            return this;
        }
        public UserAccountInformationBuilder withAccountStatus(AccountStatusEnum accountStatus)
        {
            this.accountStatus = accountStatus;
            return this;
        }
        public UserAccountInformationBuilder withIsOnline(Boolean isOnline)
        {
            this.isOnline = isOnline;
            return this;
        }
        public UserAccountInformationBuilder withCreatedAt(LocalDateTime createdAt)
        {
            this.createdAt = createdAt;
            return this;
        }
        public UserAccountInformationBuilder withSuspendedAt(LocalDateTime suspendedAt)
        {
            this.suspendedAt = suspendedAt;
            return this;
        }

        /**
         * ERROR CASES
         */
        public UserAccountInformationBuilder withFutureCreatedAt()
        {
            this.createdAt = LocalDateTime.now().plusDays(1);
            return this;
        }

        /**
         * BUILDER CONSTRUCTOR - INTERNAL CLASS OF UserDTO - UserAccountInformation
         * @return
         */
        public UserAccountInformation build()
        {
            return new UserAccountInformation(
                this.accountType,
                this.accountStatus,
                this.isOnline,
                this.createdAt,
                this.suspendedAt
            );
        }
    }

    /**
     * OBJECT - INTERNAL CLASS OF UserDTO - UserStatistics
     */
    public static class UserStatisticsBuilder
    {
        private double connectedTimes;
        private double generatedApiKeys;
        private double apiCallsNumber;

        /**
         * OBJECT CONSTRUCTOR - INTERNAL CLASS OF UserDTO- UserStatistics
         */
        public UserStatisticsBuilder()
        {
            this.connectedTimes = 0.0;
            this.generatedApiKeys = 0.0;
            this.apiCallsNumber = 0.0;
        }

        /**
         * OK CASES
         */
        public UserStatisticsBuilder withConnectedTimes(double connectedTimes)
        {
            this.connectedTimes = connectedTimes;
            return this;
        }
        public UserStatisticsBuilder withGeneratedApiKeys(double generatedApiKeys)
        {
            this.generatedApiKeys = generatedApiKeys;
            return this;
        }
        public UserStatisticsBuilder withApiCallsNumber(double apiCallsNumber)
        {
            this.apiCallsNumber = apiCallsNumber;
            return this;
        }

        /**
         * ERROR CASES
         */
        public UserStatisticsBuilder withNegativeConnectedTimes()
        {
            this.connectedTimes = -1.0; // viola @PositiveOrZero
            return this;
        }

        /**
         * BUILDER CONSTRUCTOR - INTERNAL CLASS OF UserDTO - UserStatistics
         * @return
         */
        public UserStatistics build()
        {
            return new UserStatistics(
                this.connectedTimes,
                this.generatedApiKeys,
                this.apiCallsNumber
            );
        }
    }

    /**
     * FAST BUILDERS
     */
    public static UserDTO aValidUser()
    {
        return new UserDTOTestDataBuilder().build();
    }
    public static UserDTO.UserAccountInformation aValidUserAccountInformation()
    {
        return new UserAccountInformationBuilder().build();
    }
    public static UserDTO.UserStatistics aValidUserStatistics()
    {
        return new UserStatisticsBuilder().build();
    }
}
