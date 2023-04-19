package ajou.mse.dimensionguard.dto.player.response;

import ajou.mse.dimensionguard.domain.player.Position;
import ajou.mse.dimensionguard.dto.member.response.MemberResponse;
import ajou.mse.dimensionguard.dto.player.PlayerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PlayerCompactResponse {

    @Schema(description = "PK of player", example = "3")
    private Integer id;

    @Schema(description = "회원 정보")
    private MemberResponse memberDto;

    @Schema(description = "참여중인 게임 룸의 PK", example = "2")
    private Integer roomId;

    @Schema(description = "보스 여부", example = "false")
    private Boolean isBoss;

    public static PlayerCompactResponse of(Integer id, MemberResponse memberDto, Integer roomId, Boolean isBoss) {
        return new PlayerCompactResponse(id, memberDto, roomId, isBoss);
    }

    public static PlayerCompactResponse from(PlayerDto dto) {
        return of(
                dto.getId(),
                MemberResponse.from(dto.getMemberDto()),
                dto.getRoomId(),
                dto.getIsBoss()
        );
    }
}
