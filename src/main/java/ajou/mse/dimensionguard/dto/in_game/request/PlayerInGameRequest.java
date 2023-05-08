package ajou.mse.dimensionguard.dto.in_game.request;

import ajou.mse.dimensionguard.domain.player.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PlayerInGameRequest {

    @Schema(description = "보스인지 여부", example = "false")
    @NotNull
    private Boolean isBoss;

    @Schema(description = "체력", example = "100")
    @NotNull
    private Integer hp;

    @Schema(description = "에너지", example = "50")
    @NotNull
    private Integer energy;

    // For hero
    @Schema(description = "<p>현재 위치 (for hero)" +
            "<p>해당하지 않는 경우(boss인 경우) <code>null</code>로 전달해도 된다.")
    private Position pos;

    @Schema(description = "<p>가한 데미지 (for hero)" +
            "<p>해당하지 않는 경우(boss인 경우) <code>null</code>로 전달해도 된다.",
            example = "5")
    private Integer damageDealt;

    @Schema(description = "<p>모션 (for hero)" +
            "<p>해당하지 않는 경우(boss인 경우) <code>null</code>로 전달해도 된다.",
            example = "3")
    private Integer motion;

    // For boss
    @Schema(description = "<p>사용한 스킬 (for boss)." +
            "<p>해당하지 않는 경우(hero인 경우) <code>null</code>로 전달해도 된다.")
    private SkillDto skillUsed;
}
