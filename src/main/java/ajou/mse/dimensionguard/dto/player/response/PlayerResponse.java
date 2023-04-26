package ajou.mse.dimensionguard.dto.player.response;

import ajou.mse.dimensionguard.domain.player.Position;
import ajou.mse.dimensionguard.dto.member.response.MemberResponse;
import ajou.mse.dimensionguard.dto.player.PlayerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerResponse {

    @Schema(description = "PK of player", example = "3")
    private Integer id;

    @Schema(description = "회원 정보")
    private MemberResponse memberDto;

    @Schema(description = "참여중인 게임 룸의 PK", example = "2")
    private Integer roomId;

    @Schema(description = "보스 여부", example = "false")
    private Boolean isBoss;

    @Schema(description = "체력", example = "100")
    private Integer hp;

    @Schema(description = "에너지", example = "100")
    private Integer energy;

    @Schema(description = "좌표정보")
    private Position position;

    @Schema(description = "가한 데미지", example = "5")
    private Integer damageDealt;

    @Schema(description = "현재 취하고 있는 모션", example = "2")
    private Integer motion;

    public static PlayerResponse from(PlayerDto dto) {
        return new PlayerResponse(
                dto.getId(),
                MemberResponse.from(dto.getMemberDto()),
                dto.getRoomId(),
                dto.getIsBoss(),
                dto.getHp(),
                dto.getEnergy(),
                dto.getPosition(),
                dto.getDamageDealt(),
                dto.getMotion()
        );
    }
}
