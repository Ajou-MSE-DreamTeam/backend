package ajou.mse.dimensionguard.dto.record.response;

import ajou.mse.dimensionguard.dto.member.response.MemberResponse;
import ajou.mse.dimensionguard.dto.record.PlayerRecordDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerRecordResponse {

    @Schema(description = "PK of player record", example = "7")
    private Long id;

    @Schema(description = "Member information that was played")
    private MemberResponse memberDto;

    @Schema(description = "Weather or not the player is boss", example = "false")
    private Boolean isBoss;

    @Schema(description = "Total damage dealt to the boss (Hero only)", example = "50")
    private Integer totalDamageDealt;

    @Schema(description = "The total amount of damage taken (Hero only)", example = "20")
    private Integer totalDamageTaken;

    @Schema(description = "Total number of times the skill was used (Boss only)", example = "5")
    private Integer numOfSkillUsed;

    @Schema(description = "The total number of times the skill has been hit (Boss only)", example = "3")
    private Integer numOfSkillHit;

    public static PlayerRecordResponse from(PlayerRecordDto dto) {
        return new PlayerRecordResponse(
                dto.getId(),
                MemberResponse.from(dto.getMemberDto()),
                dto.getIsBoss(),
                dto.getTotalDamageDealt(),
                dto.getTotalDamageTaken(),
                dto.getNumOfSkillUsed(),
                dto.getNumOfSkillHit()
        );
    }
}
