package ajou.mse.dimensionguard.dto.member.response;

import ajou.mse.dimensionguard.dto.member.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberResponse {

    @Schema(description = "회원의 PK", example = "1")
    private Integer id;

    @Schema(description = "계정 id", example = "test1234")
    private String accountId;

    @Schema(description = "닉네임", example = "홍길동")
    private String name;

    public static MemberResponse from(MemberDto dto) {
        return new MemberResponse(dto.getId(), dto.getAccountId(), dto.getName());
    }
}
