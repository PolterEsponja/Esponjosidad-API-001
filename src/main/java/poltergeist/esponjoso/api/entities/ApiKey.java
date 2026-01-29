package poltergeist.esponjoso.api.entities;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poltergeist.esponjoso.api.enums.ApiKeyStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiKey
{
    @NotBlank(message = "{apiKey.id.notBlank")
    private String id;
    @NotBlank(message = "{user.id.notBlank}")
    private String userId;
    @Size(min = 10, max = 10, message = "")
    private String value;
    @NotNull(message = "{apiKey.remainingUses.notNull}")
    @Size(min = 10, max = 20, message = "apiKey.remainingUses.size")
    private Integer remainingUses;
    @NotNull(message = "{apiKey.apiKeyStatus.notNull}")
    private ApiKeyStatusEnum apiKeyStatus;
    @NotNull(message = "{apiKey.createdAt.notNull}")
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

}
