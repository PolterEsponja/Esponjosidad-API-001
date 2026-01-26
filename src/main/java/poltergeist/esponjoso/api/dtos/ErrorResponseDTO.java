package poltergeist.esponjoso.api.dtos;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponseDTO(
    String code,
    String message,
    String path,
    OffsetDateTime offsetDateTime,
    List<FieldErrorItem> errors
)
{
    public static ErrorResponseDTO of(String code, String message, String path)
    {
        return new ErrorResponseDTO(code, message, path, OffsetDateTime.now(), null);
    }

    public ErrorResponseDTO withErrors(List<FieldErrorItem> errors)
    {
        return new ErrorResponseDTO(this.code, this.message, this.path, this.offsetDateTime, errors);
    }

    public static record FieldErrorItem(
        String field,
        String message,
        Object rejectedValue
    ){}
}
