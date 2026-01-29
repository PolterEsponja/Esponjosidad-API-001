package poltergeist.esponjoso.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import poltergeist.esponjoso.api.dtos.UserResponseDTO;
import poltergeist.esponjoso.api.entities.User;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserResponseMapper
{
    UserResponseDTO toUserResponseDTO(User userDTO);

    UserResponseDTO.UserAccountInformation toUserAccountInformation(User.UserAccountInformation userAccountInformation);

    UserResponseDTO.UserStatistics toUserStatistics(User.UserStatistics userStatistics);
}
