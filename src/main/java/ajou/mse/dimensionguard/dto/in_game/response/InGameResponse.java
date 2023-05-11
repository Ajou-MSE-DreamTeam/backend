package ajou.mse.dimensionguard.dto.in_game.response;

import ajou.mse.dimensionguard.dto.in_game.SkillDto;
import ajou.mse.dimensionguard.dto.player.PlayerDto;
import ajou.mse.dimensionguard.dto.player.response.PlayerResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class InGameResponse {

    @Schema(description = "Skill was used by boss", example = "10")
    private SkillDto skillUsed;

    @Schema(description = "Information about the players in the room")
    private List<PlayerResponse> players;

    public static InGameResponse of(SkillDto skillUsed, List<PlayerDto> players) {
        return new InGameResponse(
                skillUsed,
                players.stream()
                        .map(PlayerResponse::from)
                        .toList()
        );
    }
}
