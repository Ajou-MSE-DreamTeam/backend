package ajou.mse.dimensionguard.domain.player;

import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.domain.Room;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("hero")
@Entity
public class Hero extends Player {

    public static final int HERO_MAX_HP = 10;
    public static final int HERO_MAX_ENERGY = 10;

    @Embedded
    private Position pos;

    private Integer damageDealt;

    private Integer motion;

    public static Hero of(Member member, Room room) {
        return of(null, member, room, false, HERO_MAX_HP, HERO_MAX_ENERGY, null, null, null);
    }

    public static Hero of(Integer id, Member member, Room room, Boolean isReady, Integer hp, Integer energy, Position pos, Integer damageDealt, Integer motion) {
        return Hero.builder()
                .id(id)
                .member(member)
                .room(room)
                .isReady(isReady)
                .hp(hp)
                .energy(energy)
                .pos(pos)
                .damageDealt(damageDealt)
                .motion(motion)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Hero(Integer id, Member member, Room room, Boolean isReady, Integer hp, Integer energy, Position pos, Integer damageDealt, Integer motion) {
        super(id, member, room, isReady, hp, energy);
        this.pos = pos;
        this.damageDealt = damageDealt;
        this.motion = motion;
    }
}
