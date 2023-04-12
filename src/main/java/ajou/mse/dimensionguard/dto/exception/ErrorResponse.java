package ajou.mse.dimensionguard.dto.exception;

public record ErrorResponse(
        Integer code,
        String message
) {
}
