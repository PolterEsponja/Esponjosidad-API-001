package poltergeist.esponjoso.api.dtos;

import poltergeist.esponjoso.api.enums.AccountStatusEnum;
import poltergeist.esponjoso.api.enums.AccountTypeEnum;

public record UserBasicResponseDTO(
    String userId,
    Double coins,
    UserAccountInformation userAccountInformation
)
{
    public record UserAccountInformation(
        AccountTypeEnum accountType,
        AccountStatusEnum accountStatus
    ){}
}
