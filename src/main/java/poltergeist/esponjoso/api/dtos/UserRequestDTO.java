package poltergeist.esponjoso.api.dtos;

import poltergeist.esponjoso.api.validation.ValidEmail;
import poltergeist.esponjoso.api.validation.ValidPassword;
import poltergeist.esponjoso.api.validation.ValidUsername;

public record UserRequestDTO(
    @ValidUsername
    String username,

    @ValidEmail
    String email,

    @ValidPassword
    String password
)
{

}
