package poltergeist.esponjoso.api.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import poltergeist.esponjoso.api.dtos.UserBasicResponseDTO;
import poltergeist.esponjoso.api.dtos.UserDTO;
import poltergeist.esponjoso.api.dtos.UserRequestDTO;

@Service
@Validated
public class UserService
{
    public UserBasicResponseDTO getBasicUserInformation(double userId)
    {
        return null;
    }

    //TODO Remember to add @Valid at UserDTO when this service is enable
    public UserDTO createUser(@Valid UserRequestDTO userRequestDTO)
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userRequestDTO.username());
        userDTO.setEmail(userRequestDTO.email());
        userDTO.setPassword(userRequestDTO.password());
        userDTO.setUserId("DUMMY XD");
        return userDTO;
    }
}
