package poltergeist.esponjoso.api.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import poltergeist.esponjoso.api.dtos.ErrorResponseDTO;
import tools.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    /**
     * NOTE: Especial error message to response on all paths in error case
     * @param httpServletRequest Path
     * @param code Error code
     * @param message Information about the error
     * @return
     */
    private ErrorResponseDTO base(HttpServletRequest httpServletRequest, String code, String message)
    {
        return ErrorResponseDTO.of(code, message, httpServletRequest.getRequestURI());
    }

    private String msg(String key, Object[] args, String defaultMsg)
    {
        return messageSource.getMessage(
            key,
            args,
            defaultMsg,
            LocaleContextHolder.getLocale()
        );
    }

    /**
     * 400 - JSON body malformed
     * 400 - Invalid enum at body
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleHttpNotReadable(
        HttpMessageNotReadableException httpMessageNotReadableException,
        HttpServletRequest httpServletRequest
    )
    {
        Throwable cause = httpMessageNotReadableException.getCause();

        if(cause instanceof InvalidFormatException invalidFormatException &&
            invalidFormatException.getTargetType() != null &&
            invalidFormatException.getTargetType().isEnum()
        )
        {
            Class<?> enumType = invalidFormatException.getTargetType();
            String provided = String.valueOf(invalidFormatException.getValue());
            String allowed = Arrays.stream(enumType.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));

            String message = msg("{error.enum.invalid}",
                new Object[]{provided, enumType.getSimpleName(), allowed},
                "Value '" + provided + "' is not valid for " +
                enumType.getSimpleName() + ". Allowed values: " + allowed
            );

            return base(httpServletRequest, "ENUM_INVALID", message);
        }
        
        String message = msg("{error.request.unreadable}", null, "Invalid JSON or incorrect format.");
        return base(httpServletRequest, "REQUEST_UNREADABLE", message);
    }

    /**
     * 400 - PathVariable/RequestParam invalid type
     * 400 - Invalid Enum at params
     */

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleTypeMismatch(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException,
        HttpServletRequest httpServletRequest)
    {
        String name = methodArgumentTypeMismatchException.getName();
        String provided = Objects.toString(methodArgumentTypeMismatchException.getValue(), "null");
        Class<?> required = methodArgumentTypeMismatchException.getRequiredType();

        if (required != null && required.isEnum())
        {
            String allowed = Arrays.stream(required.getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            String message = msg(
                    "{error.enum.invalid.param}",
                    new Object[]{name, provided, allowed},
                    "Parameter '" + name + "' value '" + provided + "' is not valid. Allowed values: " + allowed
            );
            return base(httpServletRequest, "ENUM_INVALID", message);
        }

        String target = required != null ? required.getSimpleName() : "unknown";
        String message = msg(
                "{error.type.mismatch}",
                new Object[]{name, provided, target},
                "Parameter '" + name + "' value '" + provided + "' cannot be converted to " + target
        );
        return base(httpServletRequest, "TYPE_MISMATCH", message);
    }

    /**
     * 400 - @Valid at @RequestBody
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleValidation(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest httpServletRequest)
    {
        String message = msg("{error.validation}", null, "Validation errors found.");

        List<ErrorResponseDTO.FieldErrorItem> items = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ErrorResponseDTO.FieldErrorItem(
                        fe.getField(),
                        fe.getDefaultMessage(),
                        fe.getRejectedValue()
                ))
                .toList();

        return base(httpServletRequest, "VALIDATION_ERROR", message).withErrors(items);
    }

    /**
     * 400 - @Valid at params/path or traditional binding
     */
    @ExceptionHandler({ConstraintViolationException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleConstraintViolations(Exception exception, HttpServletRequest httpServletRequest)
    {
        String message = msg("{error.validation}", null, "Validation errors found.2");

        List<ErrorResponseDTO.FieldErrorItem> items = new ArrayList<>();
        if (exception instanceof ConstraintViolationException cve)
        {
            cve.getConstraintViolations().forEach(v -> {
                String field = v.getPropertyPath() != null ? v.getPropertyPath().toString() : null;
                items.add(new ErrorResponseDTO.FieldErrorItem(field, v.getMessage(), v.getInvalidValue()));
            });
        }
        else if (exception instanceof BindException bindException)
        {
            bindException.getFieldErrors().forEach(fe -> items.add(
                    new ErrorResponseDTO.FieldErrorItem(fe.getField(), fe.getDefaultMessage(), fe.getRejectedValue())
            ));
        }

        return base(httpServletRequest, "VALIDATION_ERROR", message).withErrors(items);
    }


    /**
     * 400 - Mandatory request param not found
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleMissingParam(
        MissingServletRequestParameterException missingServletRequestParameterException,
        HttpServletRequest httpServletRequest)
    {
        String message = msg("{error.param.missing}",
                new Object[]{missingServletRequestParameterException.getParameterName()},
                "Mandatory param not found: " + missingServletRequestParameterException.getParameterName());

        return base(httpServletRequest, "MISSING_PARAMETER", message);
    }


    /**
     * 405 - Not allowed method
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponseDTO handleMethodNotAllowed(
        HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException,
        HttpServletRequest httpServletRequest)
    {
        
        String methods = httpRequestMethodNotSupportedException.getSupportedHttpMethods() != null
                        ? httpRequestMethodNotSupportedException.getSupportedHttpMethods()
                        .stream().map(HttpMethod::name).collect(Collectors.joining(", "))
                        : "";

        String message = msg("{error.method.not.allowed}",
                new Object[]{methods},
                "Not allowed method. Use: " + methods);
        return base(httpServletRequest, "METHOD_NOT_ALLOWED", message);
    }


    /**
     * 500 - Internal server error - something unexpected
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleUnexpected(Exception exception, HttpServletRequest httpServletRequest)
    {
        String message = msg("{error.unexpected}", null, "Unexpected error has ocurred.");
        return base(httpServletRequest, "INTERNAL_ERROR", message);
    }

}
