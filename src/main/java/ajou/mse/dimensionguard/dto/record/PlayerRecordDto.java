package ajou.mse.dimensionguard.dto.record;

import ajou.mse.dimensionguard.domain.record.PlayerRecord;
import ajou.mse.dimensionguard.dto.member.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerRecordDto {

    private Long id;
    private MemberDto memberDto;
    private Long recordId;
    private Boolean isBoss;
    private Integer totalDamageDealt;
    private Integer totalDamageTaken;
    private Integer numOfSkillUsed;
    private Integer numOfSkillHit;

    public static PlayerRecordDto from(PlayerRecord entity) {
        return new PlayerRecordDto(
                entity.getId(),
                MemberDto.from(entity.getMember()),
                entity.getRecord().getId(),
                entity.getIsBoss(),
                entity.getTotalDamageDealt(),
                entity.getTotalDamageTaken(),
                entity.getNumOfSkillUsed(),
                entity.getNumOfSkillHit()
        );
    }
}
