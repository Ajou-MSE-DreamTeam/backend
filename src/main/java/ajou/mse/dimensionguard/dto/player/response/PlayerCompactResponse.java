package ajou.mse.dimensionguard.dto.player.response;

import ajou.mse.dimensionguard.dto.player.PlayerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerCompactResponse {

    @Schema(description = "PK of player", example = "3")
    private Integer id;

    @Schema(description = "PK of member", example = "1")
    private Integer memberId;

    @Schema(description = "Nickname", example = "Woogie")
    private String name;

    @Schema(description = "Weather or not the player is boss", example = "false")
    private Boolean isBoss;

    public static PlayerCompactResponse from(PlayerDto dto) {
        return new PlayerCompactResponse(
                dto.getId(),
                dto.getMemberDto().getId(),
                dto.getMemberDto().getName(),
                dto.getIsBoss()
        );
    }
}
