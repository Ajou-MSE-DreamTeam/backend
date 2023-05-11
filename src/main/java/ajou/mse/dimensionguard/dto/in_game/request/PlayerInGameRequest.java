package ajou.mse.dimensionguard.dto.in_game.request;

import ajou.mse.dimensionguard.domain.player.Position;
import ajou.mse.dimensionguard.dto.in_game.SkillDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PlayerInGameRequest {

    @Schema(description = "Weather or not the player is boss", example = "false")
    @NotNull
    private Boolean isBoss;

    @Schema(description = "Health point", example = "100")
    @NotNull
    private Integer hp;

    @Schema(description = "Energy point", example = "50")
    @NotNull
    private Integer energy;

    // For hero
    @Schema(description = "<p>Current position(for hero)" +
            "<p>if not applicable(if boss), you can pass it as <code>null</code>")
    private Position pos;

    @Schema(description = "<p>Damage dealt(for hero)" +
            "<p>if not applicable(if boss), you can pass it as <code>null</code>",
            example = "5")
    private Integer damageDealt;

    @Schema(description = "<p>Motion(for hero)" +
            "<p>if not applicable(if boss), you can pass it as <code>null</code>",
            example = "3")
    private Integer motion;

    // For boss
    @Schema(description = "<p>Skill was used(for boss)" +
            "<p>if not applicable(if hero), you can pass it as <code>null</code>")
    private SkillDto skillUsed;
}
