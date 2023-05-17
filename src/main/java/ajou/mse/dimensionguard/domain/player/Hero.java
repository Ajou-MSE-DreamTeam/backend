package ajou.mse.dimensionguard.domain.player;

import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.domain.Room;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.time.LocalDateTime;

import static ajou.mse.dimensionguard.constant.ConstantUtil.HERO_MAX_ENERGY;
import static ajou.mse.dimensionguard.constant.ConstantUtil.HERO_MAX_HP;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("hero")
@Entity
public class Hero extends Player {

    @Setter(AccessLevel.PRIVATE)
    @Embedded
    private Position pos;

    @Setter(AccessLevel.PRIVATE)
    private Integer damageDealt;

    @Setter(AccessLevel.PRIVATE)
    private Integer motion;

    public static Hero of(Member member, Room room) {
        return of(null, member, room, false, HERO_MAX_HP, HERO_MAX_ENERGY, null, null, null, null, null);
    }

    public static Hero of(Long id, Member member, Room room, Boolean isReady, Integer hp, Integer energy, Position pos, Integer damageDealt, Integer motion, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Hero(id, member, room, isReady, hp, energy, pos, damageDealt, motion, createdAt, updatedAt);
    }

    private Hero(Long id, Member member, Room room, Boolean isReady, Integer hp, Integer energy, Position pos, Integer damageDealt, Integer motion, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, member, room, isReady, hp, energy, createdAt, updatedAt);
        this.pos = pos;
        this.damageDealt = damageDealt;
        this.motion = motion;
    }

    public void update(Integer hp, Integer energy, Position pos, Integer damageDealt, Integer motion) {
        super.update(hp, energy);
        this.setPos(pos);
        this.setDamageDealt(damageDealt);
        this.setMotion(motion);
    }
}
