package poltergeist.esponjoso.api.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import poltergeist.esponjoso.api.dtos.UserBasicResponseDTO;
import poltergeist.esponjoso.api.dtos.UserRequestDTO;
import poltergeist.esponjoso.api.dtos.UserResponseDTO;
import poltergeist.esponjoso.api.entities.User;
import poltergeist.esponjoso.api.mappers.UserResponseMapper;

@Service
@Validated
public class UserService
{
    private final UserResponseMapper userResponseMapper;

    public UserService(UserResponseMapper userResponseMapper)
    {
        this.userResponseMapper = userResponseMapper;
    }

    public UserBasicResponseDTO getBasicUserInformation(double userId)
    {
        return null;
    }

    public UserResponseDTO createUser(@Valid UserRequestDTO userRequestDTO)
    {
        User userDTO = new User();
        userDTO.setUsername(userRequestDTO.username());
        userDTO.setEmail(userRequestDTO.email());
        userDTO.setPassword(userRequestDTO.password());
        userDTO.setUserId("DUMMY XD");

        UserResponseDTO useResponseDTO = userResponseMapper.toUserResponseDTO(userDTO);

        return useResponseDTO;
    }
}
