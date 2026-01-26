package poltergeist.esponjoso.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import poltergeist.esponjoso.api.dtos.UserBasicResponseDTO;
import poltergeist.esponjoso.api.dtos.UserDTO;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserBasicResponseMapper
{
    UserBasicResponseDTO toUserBasicResponseDTO(UserDTO userDTO);

    UserBasicResponseDTO.UserAccountInformation toBasicUserAccountInformation(UserDTO.UserAccountInformation userAccountInformationa);
}