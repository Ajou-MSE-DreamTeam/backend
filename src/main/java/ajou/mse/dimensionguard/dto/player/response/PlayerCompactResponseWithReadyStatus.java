package ajou.mse.dimensionguard.dto.player.response;

import ajou.mse.dimensionguard.dto.player.PlayerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerCompactResponseWithReadyStatus {

    @Schema(description = "PK of player", example = "3")
    private Long id;

    @Schema(description = "PK of member", example = "1")
    private Long memberId;

    @Schema(description = "Nickname", example = "Woogie")
    private String name;

    @Schema(description = "Weather or not the player is boss", example = "false")
    private Boolean isBoss;

    @Schema(description = "Weather or not the player is ready", example = "false")
    private Boolean isReady;

    public static PlayerCompactResponseWithReadyStatus from(PlayerDto dto) {
        return new PlayerCompactResponseWithReadyStatus(
                dto.getId(),
                dto.getMemberDto().getId(),
                dto.getMemberDto().getName(),
                dto.getIsBoss(),
                dto.getIsReady()
        );
    }
}
