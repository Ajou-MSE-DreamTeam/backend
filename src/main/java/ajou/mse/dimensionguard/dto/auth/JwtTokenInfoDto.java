package ajou.mse.dimensionguard.dto.auth;

import java.time.LocalDateTime;

public record JwtTokenInfoDto(
        String token,
        LocalDateTime expiresAt
) {
    public static JwtTokenInfoDto of(String token, LocalDateTime expiresAt) {
        return new JwtTokenInfoDto(token, expiresAt);
    }
}
