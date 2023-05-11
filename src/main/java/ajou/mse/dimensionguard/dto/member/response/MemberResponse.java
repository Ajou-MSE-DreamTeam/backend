package ajou.mse.dimensionguard.dto.member.response;

import ajou.mse.dimensionguard.dto.member.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberResponse {

    @Schema(description = "PK of member", example = "1")
    private Integer id;

    @Schema(description = "Account id", example = "test1234")
    private String accountId;

    @Schema(description = "Nickname", example = "Woogie")
    private String name;

    public static MemberResponse from(MemberDto dto) {
        return new MemberResponse(dto.getId(), dto.getAccountId(), dto.getName());
    }
}
