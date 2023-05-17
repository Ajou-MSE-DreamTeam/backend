package ajou.mse.dimensionguard.dto.in_game.response;

import ajou.mse.dimensionguard.dto.in_game.SkillDto;
import ajou.mse.dimensionguard.dto.player.response.PlayerResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class InGameResponse {

    @Schema(description = "Skill was used by boss", example = "10")
    private SkillDto skillUsed;

    @Schema(description = "Information about the players in the room")
    private List<PlayerResponse> players;
}
