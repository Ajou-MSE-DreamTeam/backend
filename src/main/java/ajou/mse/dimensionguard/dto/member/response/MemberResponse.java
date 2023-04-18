package ajou.mse.dimensionguard.dto.member.response;

import ajou.mse.dimensionguard.dto.member.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberResponse {

    @Schema(description = "회원의 PK", example = "1")
    private Integer id;

    @Schema(description = "계정 id", example = "test1234")
    private String accountId;

    public static MemberResponse of(Integer id, String accountId) {
        return new MemberResponse(id, accountId);
    }

    public static MemberResponse from(MemberDto dto) {
        return of(dto.getId(), dto.getAccountId());
    }
}
