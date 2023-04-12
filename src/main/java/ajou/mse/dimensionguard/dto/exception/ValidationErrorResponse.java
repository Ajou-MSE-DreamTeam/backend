package ajou.mse.dimensionguard.dto.exception;

import java.util.List;

public record ValidationErrorResponse(
        Integer code,
        String message,
        List<ValidationErrorDetails> errors
) {
}
