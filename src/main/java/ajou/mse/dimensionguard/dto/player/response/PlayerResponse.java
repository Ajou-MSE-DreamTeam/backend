package ajou.mse.dimensionguard.dto.player.response;

import ajou.mse.dimensionguard.domain.player.Position;
import ajou.mse.dimensionguard.dto.member.response.MemberResponse;
import ajou.mse.dimensionguard.dto.player.PlayerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerResponse {

    @Schema(description = "PK of player", example = "3")
    private Long id;

    @Schema(description = "Member information")
    private MemberResponse member;

    @Schema(description = "PK of the game room that the player participating in", example = "2")
    private Long roomId;

    @Schema(description = "Weather or not the player is boss", example = "false")
    private Boolean isBoss;

    @Schema(description = "Health point", example = "100")
    private Integer hp;

    @Schema(description = "Energy point", example = "100")
    private Integer energy;

    @Schema(description = "Position information")
    private Position pos;

    @Schema(description = "Damage dealt", example = "5")
    private Integer damageDealt;

    @Schema(description = "Motion the current player taking", example = "2")
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
