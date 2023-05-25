package ajou.mse.dimensionguard.dto.player;

import ajou.mse.dimensionguard.domain.player.Boss;
import ajou.mse.dimensionguard.domain.player.Hero;
import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.domain.player.Position;
import ajou.mse.dimensionguard.dto.member.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerDto {

    private Long id;
    private MemberDto memberDto;
    private Long roomId;
    private Boolean isBoss;
    private Boolean isReady;
    private Integer hp;
    private Integer energy;
    private Position position;
    private Integer damageDealt;
    private Integer motion;
    private Integer totalDamageDealt;
    private Integer totalDamageTaken;
    private Integer numOfSkillUsed;
    private Integer numOfSkillHit;

    public static PlayerDto from(Player entity) {
        boolean isBoss;
        Hero hero = null;
        Boss boss = null;
        if (entity instanceof Hero) {
            isBoss = false;
            hero = (Hero) entity;
        } else {
            isBoss = true;
            boss = (Boss) entity;
        }

        return new PlayerDto(
                entity.getId(),
                MemberDto.from(entity.getMember()),
                entity.getRoom().getId(),
                isBoss,
                entity.getIsReady(),
                entity.getHp(),
                entity.getEnergy(),
                hero == null ? null : hero.getPos(),
                hero == null ? null : hero.getDamageDealt(),
                hero == null ? null : hero.getMotion(),
                hero == null ? null : hero.getTotalDamageDealt(),
                hero == null ? null : hero.getTotalDamageTaken(),
                boss == null ? null : boss.getNumOfSkillUsed(),
                boss == null ? null : boss.getNumOfSkillHit()
        );
    }
}
