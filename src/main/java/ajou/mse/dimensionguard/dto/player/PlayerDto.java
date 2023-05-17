package ajou.mse.dimensionguard.dto.player;

import ajou.mse.dimensionguard.domain.player.Hero;
import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.domain.player.Position;
import ajou.mse.dimensionguard.dto.member.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static PlayerDto of(Long id, MemberDto memberDto, Long roomId, Boolean isBoss, Boolean isReady, Integer hp, Integer energy, Position position, Integer damageDealt, Integer motion) {
        return new PlayerDto(id, memberDto, roomId, isBoss, isReady, hp, energy, position, damageDealt, motion);
    }

    public static PlayerDto from(Player entity) {
        boolean isBoss = true;
        Hero hero = null;
        if (entity instanceof Hero) {
            isBoss = false;
            hero = (Hero) entity;
        }

        return of(
                entity.getId(),
                MemberDto.from(entity.getMember()),
                entity.getRoom().getId(),
                isBoss,
                entity.getIsReady(),
                entity.getHp(),
                entity.getEnergy(),
                hero == null ? null : hero.getPos(),
                hero == null ? null : hero.getDamageDealt(),
                hero == null ? null : hero.getMotion()
        );
    }
}
