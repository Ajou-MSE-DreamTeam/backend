package ajou.mse.dimensionguard.dto.in_game;

import ajou.mse.dimensionguard.domain.player.Position;
import ajou.mse.dimensionguard.dto.redis.Skill;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SkillDto {

    @Schema(description = "Skill number", example = "5")
    @NotNull
    private Integer num;

    @Schema(description = "Where the skill was used")
    @NotNull
    private Position pos;

    public static SkillDto from(Skill skillInfo) {
        return new SkillDto(skillInfo.getNum(), skillInfo.getPos());
    }
}
