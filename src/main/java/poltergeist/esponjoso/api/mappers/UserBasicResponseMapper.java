package poltergeist.esponjoso.api.mappers;

import org.mapstruct.Mapper;

import poltergeist.esponjoso.api.dtos.UserBasicResponseDTO;
import poltergeist.esponjoso.api.dtos.UserDTO;

@Mapper(componentModel = "spring")
public interface UserBasicResponseMapper
{
    UserBasicResponseDTO toUserBasicResponseDTO(UserDTO userDTO);

    UserBasicResponseDTO.UserAccountInformation toBasicUserAccountInformation(UserDTO.UserAccountInformation userAccountInformationa);
}
