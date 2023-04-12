package ajou.mse.dimensionguard.dto.exception;

public record ValidationErrorDetails(
        Integer code,
        String field,
        String message
) {
}
