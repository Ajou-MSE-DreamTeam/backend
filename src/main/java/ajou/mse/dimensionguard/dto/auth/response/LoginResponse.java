package ajou.mse.dimensionguard.dto.auth.response;

import ajou.mse.dimensionguard.dto.member.response.MemberResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginResponse {

    @Schema(description = "Information to logged in user")
    MemberResponse loggedInMember;

    @Schema(description = "Token information")
    TokenResponse token;

    public static LoginResponse of(MemberResponse loggedInMember, TokenResponse token) {
        return new LoginResponse(loggedInMember, token);
    }
}
